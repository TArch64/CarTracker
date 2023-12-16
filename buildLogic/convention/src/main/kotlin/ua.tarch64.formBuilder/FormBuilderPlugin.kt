package ua.tarch64.formBuilder

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.TaskProvider
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import ua.tarch64.formBuilder.generate.GenerateFormSourcesTask

abstract class FormBuilderPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        val task = registerGenerateSourcesTask(project)

        project.tasks.withType(KotlinCompile::class.java).all {
            dependsOn(task)
        }
    }

    private fun registerGenerateSourcesTask(project: Project): TaskProvider<GenerateFormSourcesTask> {
        return project.tasks.register("generateFormSources", GenerateFormSourcesTask::class.java)
    }
}
