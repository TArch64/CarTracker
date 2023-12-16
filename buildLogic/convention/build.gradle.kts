import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
}

group = "ua.tarch64.buildLogic"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
}

dependencies {
    compileOnly(libs.kotlin.gradlePlugin)
    implementation(libs.classgraph.core)
    implementation(libs.classgraph.jvmDriver)
}

gradlePlugin {
    plugins {
        register("formBuilder") {
            id = "ua.tarch64.formBuilder"
            implementationClass = "ua.tarch64.formBuilder.FormBuilderPlugin"
        }
    }
}
