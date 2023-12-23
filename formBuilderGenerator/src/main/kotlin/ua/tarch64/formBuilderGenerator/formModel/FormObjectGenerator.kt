package ua.tarch64.formBuilderGenerator.formModel

import com.google.devtools.ksp.getClassDeclarationByName
import com.google.devtools.ksp.getDeclaredProperties
import com.google.devtools.ksp.getFunctionDeclarationsByName
import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.symbol.*
import com.google.devtools.ksp.validate
import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ksp.toClassName
import com.squareup.kotlinpoet.ksp.toTypeName
import com.squareup.kotlinpoet.ksp.writeTo
import ua.tarch64.formBuilderGenerator.*

class FormObjectGenerator(codeGenerator: CodeGenerator, logger: KSPLogger): BaseGenerator(codeGenerator, logger) {
    private lateinit var baseObjectModelDeclaration: KSClassDeclaration
    private lateinit var fieldModelDeclaration: KSClassDeclaration

    override fun init(resolver: Resolver) {
        super.init(resolver)

        baseObjectModelDeclaration = resolver.getClassDeclarationByName(FORM_OBJECT_BASE_MODEL)!!
        fieldModelDeclaration = resolver.getClassDeclarationByName(FORM_FIELD_MODEL)!!
    }

    override fun generate(): List<KSAnnotated> {
        queryInterfaces().forEach(::generateModel)
        return emptyList()
    }

    private fun queryInterfaces(): Set<KSClassDeclaration> {
        return resolver
            .getSymbolsWithAnnotation(FORM_OBJECT_ANNOTATION)
            .filterIsInstance<KSClassDeclaration>()
            .filter(KSNode::validate)
            .toSet()
    }

    private fun generateModel(declaration: KSClassDeclaration) {
        val fields = declaration.getDeclaredProperties().map(::generateField).toList()

        generateFile(declaration) {
            val modelSpec = TypeSpec
                .classBuilder(makeModelName(declaration))
                .primaryConstructor(generateConstructor(declaration))
                .superclass(baseObjectModelDeclaration.toClassName())
                .addProperties(fields)
                .build()

            it
                .addImport(COMPOSE_PACKAGE, "remember")
                .addFunction(generateRememberFactory(declaration, modelSpec))
                .addType(modelSpec)
        }
    }

    private fun generateFile(
        declaration: KSClassDeclaration,
        generate: (FileSpec.Builder) -> FileSpec.Builder
    ) {
        FileSpec
            .builder(declaration.toClassName())
            .let(generate)
            .build()
            .writeTo(codeGenerator, Dependencies(true))
    }

    private fun makeModelName(declaration: KSClassDeclaration): String {
        return declaration.toClassName().simpleName + "Model"
    }

    private fun generateConstructor(objectDeclaration: KSClassDeclaration): FunSpec {
        return FunSpec
            .constructorBuilder()
            .addParameters(generateConstructorParameterList(objectDeclaration))
            .build()
    }

    private fun generateConstructorParameterList(objectDeclaration: KSClassDeclaration): List<ParameterSpec> {
        return objectDeclaration.getDeclaredProperties().map(::generateConstructorParameter).toList()
    }

    private fun generateConstructorParameter(declaration: KSPropertyDeclaration): ParameterSpec {
        return ParameterSpec
            .builder(declaration.simpleName.asString(), declaration.type.toTypeName())
            .build()
    }

    private fun generateField(declaration: KSPropertyDeclaration): PropertySpec {
        val name = declaration.simpleName.asString()

        val type = fieldModelDeclaration.asType(
            listOf(resolver.getTypeArgument(declaration.type, Variance.INVARIANT))
        )

        return PropertySpec
            .builder(name + "Field", type.toTypeName())
            .initializer("%N(%N)", type.toClassName().simpleName, name)
            .build()
    }

    private fun generateRememberFactory(objectDeclaration: KSClassDeclaration, modelSpec: TypeSpec): FunSpec {
        val fieldNames = modelSpec.primaryConstructor!!.parameters.map { it.name }

        return FunSpec
            .builder("remember${modelSpec.name}")
            .addParameters(modelSpec.primaryConstructor!!.parameters)
            .returns(ClassName(objectDeclaration.packageName.asString(), modelSpec.name!!))
            .addCode(
                "return %N { %N(${fieldNames.joinToString(", ") { "%N" }}) }",
                "remember",
                modelSpec.name,
                *fieldNames.toTypedArray()
            )
            .build()
    }
}
