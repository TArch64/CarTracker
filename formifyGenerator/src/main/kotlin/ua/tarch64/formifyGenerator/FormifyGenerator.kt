package ua.tarch64.formifyGenerator

import com.google.devtools.ksp.processing.*
import com.google.devtools.ksp.symbol.KSAnnotated
import ua.tarch64.formifyGenerator.visitors.objectControl.ObjectControlVisitor
import ua.tarch64.formifyGenerator.visitors.objectControl.ObjectControlCollector

class FormifyGenerator(private val codeGenerator: CodeGenerator) : SymbolProcessor {
    private var generated = false

    override fun process(resolver: Resolver): List<KSAnnotated> = singleTime {
        val objectAnnotations = resolver.getSymbolsWithAnnotation(BASE_OBJECT_ANNOTATION)
        val objectControlVisitor = ObjectControlVisitor(ObjectControlCollector(resolver), codeGenerator)

        for (objectAnnotation in objectAnnotations) {
            objectAnnotation.accept(objectControlVisitor, Unit)
        }
    }

    private fun singleTime(generate: () -> Unit): List<KSAnnotated> {
        if (!generated) {
            generate()
            generated = true
        }
        return emptyList()
    }
}
