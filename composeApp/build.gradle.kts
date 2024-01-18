import org.jetbrains.compose.ExperimentalComposeLibrary
import org.jetbrains.kotlin.gradle.dsl.KotlinCommonCompilerOptions
import org.jetbrains.kotlin.gradle.plugin.HasCompilerOptions
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.sqldelight)
}

fun configureCommonCompilerOptions(options: KotlinCommonCompilerOptions) {
    options.freeCompilerArgs.add("-Xexpect-actual-classes")
}

fun configureCommonCompilerOptions(options: HasCompilerOptions<KotlinCommonCompilerOptions>) {
    options.configure { configureCommonCompilerOptions(this) }
}

kotlin {
    androidTarget {
        compilations.all {
            configureCommonCompilerOptions(compilerOptions)
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "ComposeApp"
            isStatic = false // sqldelight has issues with linking to ios app
            binaryOption("bundleId", "ua.tarch64.car_tracker.CarTracker")
            configureCommonCompilerOptions(compilation.compilerOptions)
        }
    }

    sourceSets {
        androidMain.dependencies {
            implementation(libs.compose.ui)
            implementation(libs.compose.ui.tooling.preview)
            implementation(libs.androidx.activity.compose)
            implementation(libs.sqldelight.driver.android)
        }

        iosMain.dependencies {
            implementation(libs.sqldelight.driver.native)
        }

        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            @OptIn(ExperimentalComposeLibrary::class)
            implementation(compose.components.resources)

            implementation(libs.sqldelight.runtime)
            implementation(libs.koin.core)
            implementation(libs.koin.compose)
            implementation(libs.voyager.navigator)
            implementation(libs.voyager.transitions)
            implementation(libs.voyager.koin)
            implementation(libs.colormath.core)
            implementation(libs.colormath.compose)
            implementation(libs.composeIcons.tablerIcons)

            api(projects.formify)
            api(projects.composeQuery)
        }
    }
}

android {
    namespace = "ua.tarch64.car_tracker"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    sourceSets.named("main").configure {
        manifest.srcFile("src/androidMain/AndroidManifest.xml")
        res.srcDirs("src/androidMain/res")
        resources.srcDirs("src/commonMain/resources")
    }

    defaultConfig {
        applicationId = "ua.tarch64.car_tracker"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    dependencies {
        debugImplementation(libs.compose.ui.tooling)
    }
}

tasks.withType<KotlinCompile>().all {
    configureCommonCompilerOptions(compilerOptions)

    kotlinOptions {
        jvmTarget = "17"
    }
}

sqldelight {
    databases {
        create("Database") {
            packageName.set("storage.database")
            deriveSchemaFromMigrations.set(true)
        }
    }
}
