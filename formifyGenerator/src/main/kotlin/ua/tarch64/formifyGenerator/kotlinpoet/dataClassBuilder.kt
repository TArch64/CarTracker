package com.squareup.kotlinpoet

fun TypeSpec.Companion.dataClassBuilder(name: String, parameters: List<ParameterSpec>) = classBuilder(name)
    .addModifiers(KModifier.DATA)
    .apply {
        primaryConstructor(
            FunSpec
                .constructorBuilder()
                .addParameters(parameters)
                .build()
        )
        addProperties(
            parameters.map {
                PropertySpec
                    .builder(it.name, it.type)
                    .initializer(it.name)
                    .build()
            }
        )
    }

