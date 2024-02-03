package ua.tarch64.formifyGenerator.visitors.objectControl

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSVisitorVoid
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.asTypeName
import com.squareup.kotlinpoet.dataClassBuilder
import com.squareup.kotlinpoet.ksp.toClassName
import com.squareup.kotlinpoet.ksp.toTypeName
import com.squareup.kotlinpoet.ksp.writeTo
import ua.tarch64.formifyGenerator.ANDROIDX_PACKAGE
import ua.tarch64.formifyGenerator.CONTROL_PACKAGE
import kotlin.reflect.KProperty0

class ObjectControlVisitor(
    private val controlCollector: ObjectControlCollector,
    private val codeGenerator: CodeGenerator
): KSVisitorVoid() {
    override fun visitClassDeclaration(classDeclaration: KSClassDeclaration, data: Unit) {
        val constructorParameters = generateConstructorParameters(classDeclaration)
        val objectControls = controlCollector.getObjectControls(classDeclaration)
        val valueClassSpec = generateObjectValue(classDeclaration, objectControls)

        FileSpec
            .builder(classDeclaration.toClassName())
            .addImport(ANDROIDX_PACKAGE, "remember")
            .addFunction(generateObjectFactory(classDeclaration, constructorParameters))
            .addType(generateObjectImpl(
                objectClass = classDeclaration,
                parameters = constructorParameters,
                objectControls = objectControls,
                valueClassSpec = valueClassSpec
            ))
            .addType(valueClassSpec)
            .build()
            .writeTo(codeGenerator, Dependencies(true))
    }

    private fun generateObjectFactory(objectClass: KSClassDeclaration, parameters: List<ParameterSpec>): FunSpec {
        val factoryName = "remember" + objectClass.simpleName.getShortName()
        val composableAnnotationName = ClassName(ANDROIDX_PACKAGE, "Composable")
        val parameterNames = parameters.map { it.name }
        val parameterSpots = parameterNames.joinToString(",") { "%N" }
        val objectClassImplName = objectClass.simpleName.getShortName() + "Impl"
        val objectClassImpl = ClassName(objectClass.packageName.asString(), objectClassImplName)

        return FunSpec
            .builder(factoryName)
            .addAnnotation(composableAnnotationName)
            .addParameters(parameters)
            .returns(objectClassImpl)
            .addStatement(
                "return remember { %N($parameterSpots) }",
                objectClassImplName,
                *parameterNames.toTypedArray()
            )
            .build()
    }

    private fun generateObjectImpl(
        objectClass: KSClassDeclaration,
        parameters: List<ParameterSpec>,
        objectControls: List<ObjectControl>,
        valueClassSpec: TypeSpec
    ): TypeSpec {
        val className = objectClass.toClassName().simpleName + "Impl"

        return TypeSpec
            .classBuilder(className)
            .primaryConstructor(
                FunSpec.constructorBuilder()
                    .addModifiers(KModifier.INTERNAL)
                    .addParameters(parameters)
                    .build()
            )
            .superclass(objectClass.toClassName())
            .apply {
                parameters.forEach { param ->
                    addSuperclassConstructorParameter("%N", param.name)
                }
            }
            .addProperty(generateObjectValueGetter(objectClass, valueClassSpec))
            .addFunction(generateControlsMapBuilder(objectControls))
            .build()
    }

    private fun generateConstructorParameters(controlsClass: KSClassDeclaration): List<ParameterSpec> {
        return controlsClass.primaryConstructor!!.parameters.toList().map {
            ParameterSpec
                .builder(it.name!!.getShortName(), it.type.toTypeName())
                .build()
        }
    }

    private fun generateControlsMapBuilder(controls: List<ObjectControl>): FunSpec {
        val controlPairs = controls.map { "    ::%N" to it.name }
        val controlKeys = controlPairs.joinToString(",\n") { it.first }

        return FunSpec
            .builder("buildControlRefs")
            .addModifiers(KModifier.OVERRIDE, KModifier.PROTECTED)
            .returns(
                List::class.asTypeName().parameterizedBy(
                    KProperty0::class.asTypeName().parameterizedBy(
                        ClassName(CONTROL_PACKAGE, "FormControl")
                    )
                )
            )
            .addStatement(
                "return listOf(\n$controlKeys\n)",
                *controlPairs.map { it.second }.toTypedArray()
            )
            .build()
    }

    private fun generateObjectValue(objectClass: KSClassDeclaration, controls: List<ObjectControl>): TypeSpec {
        val className = objectClass.toClassName().simpleName + "Value"

        val parameters = controls.filterIsInstance(ObjectFieldControl::class.java).map {
            val name = it.name.removeSuffix("Control")
            val type = it.valueTypeClass.toClassName()
            ParameterSpec.builder(name, type).build()
        }

        return TypeSpec.dataClassBuilder(className, parameters).build()
    }

    private fun generateObjectValueGetter(objectClass: KSClassDeclaration, valueClassSpec: TypeSpec): PropertySpec {
        val type = ClassName(objectClass.packageName.asString(), valueClassSpec.name!!)
        val parameterSpots = valueClassSpec.propertySpecs.joinToString(",\n") { "    %N = %NControl.value" }
        val parameterNames = valueClassSpec.propertySpecs.flatMap { listOf(it.name, it.name) }

        return PropertySpec
            .builder("value", type)
            .getter(
                FunSpec
                    .getterBuilder()
                    .addStatement(
                        "return %N(\n${parameterSpots}\n)",
                        valueClassSpec.name!!,
                        *parameterNames.toTypedArray()
                    )
                    .build()
            )
            .build()
    }
}
