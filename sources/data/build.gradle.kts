import com.google.devtools.ksp.gradle.KspAATask
import org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.android.kotlin.multiplatform.library)
    alias(libs.plugins.ksp)
    //id("shared-koin")
}

kotlin {
    androidLibrary {
        namespace = "com.example.data"
        compileSdk = 35
        minSdk = 26
    }

    val xcfName = "dataKit"

    iosX64 {
        binaries.framework {
            baseName = xcfName
        }
    }

    iosArm64 {
        binaries.framework {
            baseName = xcfName
        }
    }

    iosSimulatorArm64 {
        binaries.framework {
            baseName = xcfName
        }
    }

    sourceSets {
        commonMain {
            kotlin.srcDir("build/generated/ksp/metadata/commonMain/kotlin")
            dependencies {
                implementation(libs.koin.core)
                implementation(libs.koin.annotations)

                implementation(libs.kotlin.stdlib)
                implementation(libs.androidx.datastore.preferences)
            }
        }
        androidMain {
            kotlin.srcDir("build/generated/ksp/android/androidMain/kotlin")
            dependencies {

            }
        }
        iosMain {
            dependencies {

            }
        }
    }
}

dependencies {
    dependencies {
        configurations.forEach {
            if(it.name.contains("ksp")) {
                add(it.name, libs.koin.compiler)
            }
        }
    }
    /*listOf(
        //"kspCommonMainMetadata",
        //"kspAndroid",
        //"kspIosArm64",
        //"kspIosX64",
        //"kspIosSimulatorArm64",
    ).forEach { config ->
        add(config, libs.koin.compiler)
    }*/
}

tasks.withType<KotlinCompilationTask<*>>().configureEach {
    if (name != "kspCommonMainKotlinMetadata") {
        dependsOn("kspCommonMainKotlinMetadata")
    }
}
tasks.withType<KspAATask>().configureEach {
    if (name != "kspCommonMainKotlinMetadata") {
        dependsOn("kspCommonMainKotlinMetadata")
    }
}