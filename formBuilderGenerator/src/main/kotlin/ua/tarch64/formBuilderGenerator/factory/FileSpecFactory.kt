package ua.tarch64.formBuilderGenerator.factory

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.TypeSpec
import ua.tarch64.formBuilderGenerator.COMPOSE_PACKAGE

class FileSpecFactory {
    fun buildObjectFile(packageName: String, factorySpec: FunSpec, modelSpec: TypeSpec): FileSpec {
        return FileSpec.builder(ClassName(packageName, modelSpec.name!!)).run {
            addImport(COMPOSE_PACKAGE, "remember")
            addFunction(factorySpec)
            addType(modelSpec)
            build()
        }
    }
}
