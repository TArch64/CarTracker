package ua.tarch64.formBuilderGenerator

import com.google.devtools.ksp.processing.*
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSNode
import com.google.devtools.ksp.validate
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.ksp.writeTo

private const val ANNOTATION_PACKAGE = "ua.tarch64.formBuilder.annotation"
private const val FORM_MODEL_ANNOTATION = "$ANNOTATION_PACKAGE.FormModel"

class FormBuilderGenerator(
    private val codeGenerator: CodeGenerator,
    private val logger: KSPLogger
) : SymbolProcessor {
    override fun process(resolver: Resolver): List<KSAnnotated> {
        FileSpec
            .builder("screens.cars.create", "CarCreateForm")
            .addFunction(
                FunSpec
                    .builder("testKspGen")
                    .addParameter("id", Int::class)
                    .returns(Int::class)
                    .addCode("return id")
                    . build()
            )
            .build()
            .writeTo(codeGenerator, Dependencies(true))

        val modelInterfaces = getModelInterfaces(resolver)
        println(modelInterfaces)
        return emptyList()
    }

    private fun getModelInterfaces(resolver: Resolver): Set<KSClassDeclaration> {
        return resolver
            .getSymbolsWithAnnotation(FORM_MODEL_ANNOTATION)
            .filterIsInstance<KSClassDeclaration>()
            .filter(KSNode::validate)
            .toSet()
    }
}