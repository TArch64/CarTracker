import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrainsCompose)
}

group = "ua.tarch64.formBuilder"
version = "1.0.0"

buildscript {
    dependencies {
        classpath(libs.classgraph.core)
        classpath(libs.classgraph.jvmDriver)
    }
}

kotlin {
    jvm()
    androidTarget()
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        androidMain.dependencies {
            implementation(libs.compose.ui)
        }

        iosMain.dependencies {}

        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
        }
    }
}

android {
    namespace = "ua.tarch64.formBuilder"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

tasks.withType<KotlinCompile>().all {
    kotlinOptions {
        jvmTarget = "17"
    }
}
