package ua.tarch64.formBuilderGenerator

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.symbol.KSAnnotated

abstract class BaseGenerator(
    protected val codeGenerator: CodeGenerator,
    protected val logger: KSPLogger,
) {
    protected lateinit var resolver: Resolver

    open fun init(resolver: Resolver) {
        this.resolver = resolver
    }

    abstract fun generate(): List<KSAnnotated>
}
