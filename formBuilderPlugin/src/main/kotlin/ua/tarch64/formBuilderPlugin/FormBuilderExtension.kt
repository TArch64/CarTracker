package ua.tarch64.formBuilderPlugin

import org.gradle.api.provider.Property

interface FormBuilderExtension {
    val packageName: Property<String>
}
