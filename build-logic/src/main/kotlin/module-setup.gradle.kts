import org.gradle.kotlin.dsl.kotlin

interface ModuleSetupExtension {
    val name: Property<String>
}

val extension = extensions.create<ModuleSetupExtension>("moduleSetup")
extension.name.convention(project.name)


plugins {
    kotlin("multiplatform")
    id("com.android.kotlin.multiplatform.library")
}

kotlin {
    val moduleName = extension.name.get()

    androidLibrary {
        namespace = "com.example.$moduleName"
        compileSdk = 35
        minSdk = 26
    }

    val xcfName = "${moduleName}Kit"

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = xcfName
            isStatic = true
        }
    }
}