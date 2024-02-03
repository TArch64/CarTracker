package ua.tarch64.formifyGenerator.visitors.objectControl

import com.google.devtools.ksp.getAllSuperTypes
import com.google.devtools.ksp.getClassDeclarationByName
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSPropertyDeclaration
import com.squareup.kotlinpoet.ksp.toClassName
import ua.tarch64.formifyGenerator.BASE_CONTROL_CLASS
import ua.tarch64.formifyGenerator.FIELD_CONTROL_CLASS

class ObjectControlCollector(private val resolver: Resolver) {
    fun getObjectControls(controlClass: KSClassDeclaration): List<ObjectControl> {
        return controlClass.getAllProperties().map(::getControl).filterNotNull().toList()
    }

    private fun getControl(property: KSPropertyDeclaration): ObjectControl? {
        val propertyType = property.type.resolve()
        val typeName = propertyType.toClassName().canonicalName
        val typeClass = resolver.getClassDeclarationByName(typeName)!!

        if (!isFormControl(typeClass)) {
            return null
        }

        if (isFormFieldControl(typeClass)) {
            val valueTypeName = propertyType.arguments.first().type!!.resolve().toClassName().canonicalName

            return ObjectFieldControl(
                property = property,
                typeClass = typeClass,
                valueTypeClass = resolver.getClassDeclarationByName(valueTypeName)!!
            )
        }

        return ObjectControl(property = property, typeClass = typeClass)
    }

    private fun isFormControl(typeClass: KSClassDeclaration): Boolean {
        return typeClass.getAllSuperTypes().any { it.toClassName().canonicalName == BASE_CONTROL_CLASS }
    }

    private fun isFormFieldControl(typeClass: KSClassDeclaration): Boolean {
        return typeClass.toClassName().canonicalName == FIELD_CONTROL_CLASS
    }
}
