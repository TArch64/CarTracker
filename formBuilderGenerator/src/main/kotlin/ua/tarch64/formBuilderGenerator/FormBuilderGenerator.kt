package ua.tarch64.formBuilderGenerator

import com.google.devtools.ksp.processing.*
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSNode
import com.google.devtools.ksp.validate
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.ksp.writeTo
import ua.tarch64.formBuilderGenerator.factory.ClassSpecFactory
import ua.tarch64.formBuilderGenerator.factory.FileSpecFactory
import ua.tarch64.formBuilderGenerator.factory.FunSpecFactory

class FormBuilderGenerator(
    private val codeGenerator: CodeGenerator,
    private val fileFactory: FileSpecFactory,
    private val classSpecFactory: ClassSpecFactory,
    private val funSpecFactory: FunSpecFactory
) : SymbolProcessor {
    private var generated = false

    override fun process(resolver: Resolver): List<KSAnnotated> = singleTime {
        classSpecFactory.init(resolver, funSpecFactory)

        val objectInterfaces = queryObjectInterfaces(resolver)
        val fileSpecs = objectInterfaces.map(::buildObjectFileSpec)

        for (fileSpec in fileSpecs) {
            fileSpec.writeTo(codeGenerator, Dependencies(true))
        }

        emptyList()
    }

    private fun singleTime(generate: () -> List<KSAnnotated>): List<KSAnnotated> {
        return if (generated) emptyList() else generate().also { generated = true }
    }

    private fun queryObjectInterfaces(resolver: Resolver): Set<KSClassDeclaration> {
        return resolver
            .getSymbolsWithAnnotation(FORM_OBJECT_ANNOTATION)
            .filterIsInstance<KSClassDeclaration>()
            .filter(KSNode::validate)
            .toSet()
    }

    private fun buildObjectFileSpec(declaration: KSClassDeclaration): FileSpec {
        val modelSpec = classSpecFactory.buildObjectModel(declaration)

        return fileFactory.buildObjectFile(
            packageName = declaration.packageName.asString(),
            modelSpec = modelSpec,
            factorySpec = funSpecFactory.buildObjectFactory(declaration, modelSpec),
        )
    }
}
