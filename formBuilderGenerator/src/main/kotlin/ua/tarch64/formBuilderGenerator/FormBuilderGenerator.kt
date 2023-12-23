package ua.tarch64.formBuilderGenerator

import com.google.devtools.ksp.processing.*
import com.google.devtools.ksp.symbol.KSAnnotated
import ua.tarch64.formBuilderGenerator.formModel.FormObjectGenerator

class FormBuilderGenerator(
    private val formModelGenerator: FormObjectGenerator
) : SymbolProcessor {
    private var generated = false

    override fun process(resolver: Resolver): List<KSAnnotated> = generate {
        formModelGenerator.init(resolver)
        return@generate formModelGenerator.generate()
    }

    private fun generate(generate: () -> List<KSAnnotated>): List<KSAnnotated> {
        return if (generated) emptyList() else generate().also { generated = true }
    }
}
