plugins {
    kotlin("jvm")
    alias(libs.plugins.pluginPublish)
    `java-gradle-plugin`
}

group = "ua.tarch64.formBuilderPlugin"
version = "1.0.0"

dependencies {
    implementation(kotlin("stdlib"))
    implementation(gradleApi())
}

gradlePlugin {
    val formBuilderPlugin by plugins.creating {
        id = "ua.tarch64.formBuilderPlugin"
        version = "1.0.0"
        displayName = "formBuilderPlugin"
        description = "Plugin to generate form builder sources"
        tags.set(listOf("kotlin", "multiplatform", "forms"))
        implementationClass = "ua.tarch64.formBuilderPlugin.FormBuilderPlugin"
    }

    website.set("https://github.com/TArch64/CarTracker")
    vcsUrl.set("https://github.com/TArch64/CarTracker.git")
}

publishing {
    repositories {
        maven {
            name = "localPluginRepository"
            url = uri("../local-plugin-repository")
        }
    }
}