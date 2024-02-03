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
import ua.tarch64.formifyGenerator.COMPOSE_RUNTIME_PACKAGE
import ua.tarch64.formifyGenerator.CONTROL_PACKAGE
import kotlin.reflect.KProperty0

private const val INDENT = "  "

class ObjectControlVisitor(
    private val controlCollector: ObjectControlCollector,
    private val codeGenerator: CodeGenerator
): KSVisitorVoid() {
    private lateinit var objectClass: KSClassDeclaration
    private val objectPackageName get() = objectClass.packageName.asString()
    private val objectClassName get() = objectClass.toClassName().simpleName
    private val objectClassFactoryName get() = "remember$objectClassName"
    private val objectClassImplClassName get() = ClassName(objectPackageName, "${objectClassName}Impl")
    private val objectClassValueClassName get() = ClassName(objectPackageName, "${objectClassName}Value")

    private lateinit var objectControls: List<ObjectControl>
    private lateinit var constructorParameterSpecs: List<ParameterSpec>
    private lateinit var valueClassSpec: TypeSpec

    private val controlRefsType get() = List::class.asTypeName().parameterizedBy(
        KProperty0::class.asTypeName().parameterizedBy(
            ClassName(CONTROL_PACKAGE, "FormControl")
        )
    )

    override fun visitClassDeclaration(classDeclaration: KSClassDeclaration, data: Unit) {
        objectClass = classDeclaration
        constructorParameterSpecs = generateConstructorParameters()
        objectControls = controlCollector.getObjectControls(classDeclaration)
        valueClassSpec = generateObjectValue()

        FileSpec
            .builder(classDeclaration.toClassName())
            .addImport(COMPOSE_RUNTIME_PACKAGE, "remember")
            .addFunction(generateObjectFactory())
            .addType(generateObjectImpl())
            .addType(valueClassSpec)
            .build()
            .writeTo(codeGenerator, Dependencies(true))
    }

    private fun generateObjectFactory(): FunSpec {
        val parameterNames = constructorParameterSpecs.map { it.name }
        val parameterSpots = parameterNames.joinToString(", \n") { "$INDENT$INDENT%N" }

        return FunSpec
            .builder(objectClassFactoryName)
            .addAnnotation(ClassName(COMPOSE_RUNTIME_PACKAGE, "Composable"))
            .addParameters(constructorParameterSpecs)
            .returns(objectClassImplClassName)
            .addStatement(
                "return remember {\n $INDENT%N(\n$parameterSpots\n$INDENT) \n}",
                objectClassImplClassName.simpleName,
                *parameterNames.toTypedArray()
            )
            .build()
    }

    private fun generateObjectImpl(): TypeSpec {
        return TypeSpec
            .classBuilder(objectClassImplClassName)
            .primaryConstructor(
                FunSpec.constructorBuilder()
                    .addModifiers(KModifier.INTERNAL)
                    .addParameters(constructorParameterSpecs)
                    .build()
            )
            .superclass(objectClass.toClassName())
            .apply {
                constructorParameterSpecs.forEach { param ->
                    addSuperclassConstructorParameter("%N", param.name)
                }
            }
            .addProperty(generateObjectValueGetter())
            .addFunction(generateControlsMapBuilder())
            .build()
    }

    private fun generateConstructorParameters(): List<ParameterSpec> {
        return objectClass.primaryConstructor!!.parameters.toList().map {
            ParameterSpec
                .builder(it.name!!.getShortName(), it.type.toTypeName())
                .build()
        }
    }

    private fun generateControlsMapBuilder(): FunSpec {
        val controlPairs = objectControls.map { "$INDENT::%N" to it.controlName }
        val controlKeys = controlPairs.joinToString(",\n") { it.first }

        return FunSpec
            .builder("buildControlRefs")
            .addModifiers(KModifier.OVERRIDE, KModifier.PROTECTED)
            .returns(controlRefsType)
            .addStatement(
                "return listOf(\n$controlKeys\n)",
                *controlPairs.map { it.second }.toTypedArray()
            )
            .build()
    }

    private fun generateObjectValue(): TypeSpec {
        val parameters = objectControls.filterIsInstance(ObjectFieldControl::class.java).map {
            ParameterSpec.builder(it.name, it.valueTypeClass.toClassName()).build()
        }

        return TypeSpec.dataClassBuilder(objectClassValueClassName.simpleName, parameters).build()
    }

    private fun generateObjectValueGetter(): PropertySpec {
        val parameterSpots = valueClassSpec.propertySpecs.joinToString(",\n") { "$INDENT%N = %NControl.value" }
        val parameterNames = valueClassSpec.propertySpecs.flatMap { listOf(it.name, it.name) }

        return PropertySpec
            .builder("value", objectClassValueClassName)
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
