import com.google.devtools.ksp.gradle.KspAATask
import org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.android.kotlin.multiplatform.library)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.ksp)
    //id("shared-koin")
}

kotlin {

    androidLibrary {
        namespace = "com.example.feature"
        compileSdk = 35
        minSdk = 26
    }

    val xcfName = "featureKit"

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
                implementation(project(":core"))
                implementation(project(":api"))
                implementation(project(":network"))

                implementation(libs.koin.core)
                implementation(libs.koin.annotations)

                implementation(libs.kotlin.stdlib)
                implementation(libs.androidx.datastore.preferences)
                implementation(libs.androidx.lifecycle.viewmodel)
                implementation(libs.kotlinx.serialization.core)
                implementation(libs.kotlinx.serialization.json)
                implementation(libs.bundles.ktorClientCommon)
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

        commonTest {
            dependencies {
                implementation(libs.kotlin.test)
            }
        }

    }
}

dependencies {
    configurations.forEach {
        if(it.name.contains("ksp")) {
            add(it.name, libs.koin.compiler)
        }
    }
}

/*dependencies {
    listOf(
        "kspCommonMainMetadata",
        //"kspAndroid",
        //"kspIosArm64",
        //"kspIosX64",
        // "kspIosSimulatorArm64",
    ).forEach { config ->
        add(config, libs.koin.compiler)
    }
}*/

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

gradle.taskGraph.whenReady {
    tasks.forEach { task ->
        println("${task.name} -> ${task::class.qualifiedName}")
    }
}