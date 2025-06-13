plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.android.kotlin.multiplatform.library)
    alias(libs.plugins.kotlinSerialization)
    id("shared-koin")
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
            dependencies {
                implementation(project(":core"))
                implementation(project(":api"))
                implementation(project(":network"))

                implementation(libs.kotlin.stdlib)
                implementation(libs.androidx.datastore.preferences)
                implementation(libs.androidx.lifecycle.viewmodel)
                implementation(libs.kotlinx.serialization.core)
                implementation(libs.kotlinx.serialization.json)
                implementation(libs.bundles.ktorClientCommon)
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

        commonTest {
            dependencies {
                implementation(libs.kotlin.test)
            }
        }

    }

}

ksp {
    arg("KOIN_DEFAULT_MODULE","false")
}