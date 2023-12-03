package ua.tarch64.formBuilderPlugin

import org.gradle.api.DefaultTask
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.options.Option

abstract class BuildFormTask : DefaultTask() {
    init {
        description = "Generate form sources"
    }

    @get:Input
    @get:Option(option = "packageName", description = "Generated sources package name")
    abstract val packageName: Property<String>

    @TaskAction()
    fun generateSources() {
        println("Form package name ${packageName.get()}")
    }
}