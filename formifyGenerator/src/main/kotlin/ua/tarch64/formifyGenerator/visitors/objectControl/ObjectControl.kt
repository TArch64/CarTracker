package ua.tarch64.formifyGenerator.visitors.objectControl

import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSPropertyDeclaration

open class ObjectControl(
    val property: KSPropertyDeclaration,
    val typeClass: KSClassDeclaration
) {
    val controlName get() = property.simpleName.getShortName()
    val name get() = controlName.removeSuffix("Control")
}

class ObjectFieldControl(
    property: KSPropertyDeclaration,
    typeClass: KSClassDeclaration,
    val valueTypeClass: KSClassDeclaration
): ObjectControl(property, typeClass)
