package ua.tarch64.formBuilderGenerator

import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.processing.SymbolProcessorProvider
import ua.tarch64.formBuilderGenerator.formModel.FormObjectGenerator

class FormBuilderGeneratorProvider : SymbolProcessorProvider {
    override fun create(environment: SymbolProcessorEnvironment): SymbolProcessor {
        return FormBuilderGenerator(
            formModelGenerator = FormObjectGenerator(
                codeGenerator = environment.codeGenerator,
                logger = environment.logger,
            )
        )
    }
}
