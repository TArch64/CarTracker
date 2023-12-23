package ua.tarch64.formBuilderGenerator.factory

import com.google.devtools.ksp.getDeclaredProperties
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSPropertyDeclaration
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.ksp.toTypeName

class FunSpecFactory {
    fun buildObjectConstructor(objectDeclaration: KSClassDeclaration): FunSpec {
        return FunSpec.constructorBuilder().run {
            addParameters(objectDeclaration.getDeclaredProperties().map(::buildObjectConstructorParameter).toList())
            build()
        }
    }

    fun buildObjectFactory(objectDeclaration: KSClassDeclaration, modelSpec: TypeSpec): FunSpec {
        return FunSpec.builder("remember${modelSpec.name}").run {
            val parameterNames = modelSpec.primaryConstructor!!.parameters.map { it.name }

            addParameters(modelSpec.primaryConstructor!!.parameters)
            returns(ClassName(objectDeclaration.packageName.asString(), modelSpec.name!!))

            addCode(
                "return %N { %N(${parameterNames.joinToString(", ") { "%N" }}) }",
                "remember",
                modelSpec.name,
                *parameterNames.toTypedArray()
            )

            build()
        }
    }

    private fun buildObjectConstructorParameter(declaration: KSPropertyDeclaration): ParameterSpec {
        return ParameterSpec
            .builder(declaration.simpleName.asString(), declaration.type.toTypeName())
            .build()
    }
}
