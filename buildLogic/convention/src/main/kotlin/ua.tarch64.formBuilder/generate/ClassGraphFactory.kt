package ua.tarch64.formBuilder.generate

import io.github.classgraph.ClassGraph
import io.github.classgraph.ClassGraph.CircumventEncapsulationMethod
import org.gradle.api.file.FileTree
import java.net.URLClassLoader

class ClassGraphFactory(private val source: FileTree) {
    init {
        ClassGraph.CIRCUMVENT_ENCAPSULATION = CircumventEncapsulationMethod.JVM_DRIVER
    }

    private val classPathUrls get() = source.files.map { it.toURI().toURL() }
    private val classLoader get() = URLClassLoader(classPathUrls.toTypedArray())

    fun buildClassGraph(): ClassGraph {
        return ClassGraph().addClassLoader(classLoader)
    }
}