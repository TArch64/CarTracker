package ua.tarch64.formBuilderGenerator

import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.processing.SymbolProcessorProvider
import ua.tarch64.formBuilderGenerator.factory.ClassSpecFactory
import ua.tarch64.formBuilderGenerator.factory.FileSpecFactory
import ua.tarch64.formBuilderGenerator.factory.FunSpecFactory

class FormBuilderGeneratorProvider : SymbolProcessorProvider {
    override fun create(environment: SymbolProcessorEnvironment): SymbolProcessor {
        return FormBuilderGenerator(
            codeGenerator = environment.codeGenerator,
            fileFactory = FileSpecFactory(),
            classSpecFactory = ClassSpecFactory(),
            funSpecFactory = FunSpecFactory()
        )
    }
}
