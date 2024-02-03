package ua.tarch64.formifyGenerator.visitors.objectControl

import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSPropertyDeclaration

data class ObjectControl(
    val property: KSPropertyDeclaration,
    val typeClass: KSClassDeclaration
) {
    val name get() = property.simpleName.getShortName()
}
