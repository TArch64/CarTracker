package ua.tarch64.formBuilderPlugin

import org.gradle.api.Plugin
import org.gradle.api.Project

class FormBuilderPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        val extension = createExtension(project)
        registerBuildFormTask(project, extension)
    }

    private fun createExtension(project: Project): FormBuilderExtension {
        return project.extensions.create("formBuilder", FormBuilderExtension::class.java)
    }

    private fun registerBuildFormTask(project: Project, extension: FormBuilderExtension) {
        project.tasks.register("buildForm", BuildFormTask::class.java) {
            it.packageName.set(extension.packageName)
        }
    }
}