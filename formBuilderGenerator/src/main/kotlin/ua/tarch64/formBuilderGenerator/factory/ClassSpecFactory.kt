package ua.tarch64.formBuilderGenerator.factory

import com.google.devtools.ksp.getClassDeclarationByName
import com.google.devtools.ksp.getDeclaredProperties
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSPropertyDeclaration
import com.google.devtools.ksp.symbol.Variance
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.ksp.toClassName
import com.squareup.kotlinpoet.ksp.toTypeName
import ua.tarch64.formBuilderGenerator.FORM_FIELD_MODEL
import ua.tarch64.formBuilderGenerator.FORM_OBJECT_BASE_MODEL

class ClassSpecFactory {
    private lateinit var resolver: Resolver
    private lateinit var funSpecFactory: FunSpecFactory
    private lateinit var baseObjectModelDeclaration: KSClassDeclaration
    private lateinit var fieldModelDeclaration: KSClassDeclaration

    fun init(resolver: Resolver, funSpecFactory: FunSpecFactory) {
        this.resolver = resolver
        this.funSpecFactory = funSpecFactory

        baseObjectModelDeclaration = resolver.getClassDeclarationByName(FORM_OBJECT_BASE_MODEL)!!
        fieldModelDeclaration = resolver.getClassDeclarationByName(FORM_FIELD_MODEL)!!
    }

    fun buildObjectModel(declaration: KSClassDeclaration): TypeSpec {
        val className = "${declaration.toClassName().simpleName}Model"
        val properties = declaration.getDeclaredProperties().map(::buildObjectProperty).toList()

        return TypeSpec.classBuilder(className).run {
            primaryConstructor(funSpecFactory.buildObjectConstructor(declaration))
            superclass(baseObjectModelDeclaration.toClassName())
            addProperties(properties)
            build()
        }
    }

    private fun buildObjectProperty(declaration: KSPropertyDeclaration): PropertySpec {
        val type = fieldModelDeclaration.asType(listOf(
            resolver.getTypeArgument(declaration.type, Variance.INVARIANT)
        ))

        val name = declaration.simpleName.asString()

        return PropertySpec.builder("${name}Field", type.toTypeName()).run {
            initializer("%N(%N)", type.toClassName().simpleName, name)
            build()
        }
    }
}
