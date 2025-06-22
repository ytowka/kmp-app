plugins {
    alias(libs.plugins.ksp)
    id("shared-koin")
    id("module-setup")
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                implementation(libs.kotlin.stdlib)

                api(libs.kotlinx.coroutines.core)
                api(libs.androidx.lifecycle.viewmodel)
                api(libs.napier)
            }
        }
        androidMain {
            dependencies {
                api(libs.kotlinx.coroutines.android)
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