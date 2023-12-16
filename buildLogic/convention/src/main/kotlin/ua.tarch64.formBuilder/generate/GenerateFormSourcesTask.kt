package ua.tarch64.formBuilder.generate

import org.gradle.api.tasks.SourceTask
import org.gradle.api.tasks.TaskAction

abstract class GenerateFormSourcesTask : SourceTask() {
    companion object {
        private const val LIB_PACKAGE = "ua.tarch64.formBuilder.annotation"
        private const val FORM_MODEL_CLASSPATH = "$LIB_PACKAGE.FormModel"
    }

    init {
        description = "Generate form sources"
        source = project.fileTree("src/commonMain/kotlin")
//        outputDir = project.layout.buildDirectory.dir("generated/formBuilder")
    }

    @TaskAction
    fun execute() {
        val classGraph = ClassGraphFactory(source).buildClassGraph()
        val scanResult = classGraph.enableAllInfo().scan()
        val modelInterfaces = scanResult.getClassesWithAnnotation(FORM_MODEL_CLASSPATH)
        println(modelInterfaces.names)
        scanResult.close()
    }
}