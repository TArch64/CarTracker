package ua.tarch64.formifyGenerator.visitors.objectControl

import com.google.devtools.ksp.getAllSuperTypes
import com.google.devtools.ksp.getClassDeclarationByName
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSPropertyDeclaration
import com.squareup.kotlinpoet.ksp.toClassName
import ua.tarch64.formifyGenerator.BASE_CONTROL_CLASS

class ObjectControlCollector(private val resolver: Resolver) {
    private val cache = mutableMapOf<KSPropertyDeclaration, KSClassDeclaration>()

    fun getObjectControls(controlClass: KSClassDeclaration): List<ObjectControl> {
        return controlClass.getAllProperties().map(::getControl).filterNotNull().toList()
    }

    fun getControl(property: KSPropertyDeclaration): ObjectControl? {
        return getPropertyClassType(property)?.let { (ObjectControl(property, it)) }
    }

    private fun getPropertyClassType(property: KSPropertyDeclaration): KSClassDeclaration? {
        if (cache.contains(property)) {
            return cache[property]!!
        }
        val typeName = property.type.resolve().toClassName().canonicalName
        val typeClass = resolver.getClassDeclarationByName(typeName)!!

        if (!isFormControl(typeClass)) {
            return null
        }

        cache[property] = typeClass
        return typeClass
    }

    private fun isFormControl(typeClass: KSClassDeclaration): Boolean {
        return typeClass.getAllSuperTypes().any { it.toClassName().canonicalName == BASE_CONTROL_CLASS }
    }
}
