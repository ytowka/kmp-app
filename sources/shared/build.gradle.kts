import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinSerialization)
    id("shared-koin")
    alias(libs.plugins.skie)
}

android {
    namespace = "com.example.kmpapp"
    compileSdk = 35
    defaultConfig {
        minSdk = 26
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

kotlin {
    androidTarget {
        compilations.all {
            compileTaskProvider.configure {
                compilerOptions {
                    jvmTarget.set(JvmTarget.JVM_1_8)
                }
            }
        }
    }
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
            isStatic = true
            export(projects.core)
            export(projects.network)
            export(projects.data)
            export(projects.feature)
            export(projects.api)
//            export(project(":core"))
//            export(project(":network"))
//            export(project(":data"))
//            export(project(":feature"))
        }
    }

    sourceSets {
        commonMain.dependencies {
            api(project(":core"))
            api(project(":network"))
            api(project(":data"))
            api(project(":feature"))
            api(project(":api"))

            implementation(libs.kotlinx.serialization.core)
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.kotlinx.datetime)

            implementation(libs.multiplatform.settings)
            implementation(libs.multiplatform.settings.serialization)

            implementation(libs.jwt.parser)

            implementation(libs.androidx.lifecycle.viewmodel)
        }
        androidMain.dependencies {
            implementation(libs.compose.ui)
            implementation(libs.compose.ui.tooling.preview)
            implementation(libs.compose.ui.tooling)
            implementation(libs.compose.material3)
            implementation(libs.compose.runtime)
            implementation(libs.compose.foundation)
            implementation(libs.androidx.ui.icons)

            implementation(libs.androidx.lifecycle.runtime)
            implementation(libs.androidx.lifecycle.runtime.compose)
            implementation(libs.androidx.lifecycle.viewmodel.compose)

            implementation(libs.coil)
        }
        iosMain.dependencies {

        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}