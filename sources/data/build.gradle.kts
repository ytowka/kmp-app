plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.android.kotlin.multiplatform.library)
    alias(libs.plugins.ksp)
    id("shared-koin")
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
            dependencies {
                implementation(libs.kotlin.stdlib)
                implementation(libs.androidx.datastore.preferences)
            }
        }
        androidMain {
            dependencies {

            }
        }
        iosMain {
            dependencies {

            }
        }
    }
}