plugins {
    id("module-setup")
    id("shared-koin")
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                api(project(":api"))

                implementation(libs.bundles.ktorClientCommon)
                implementation(libs.kotlin.stdlib)
            }
        }
        androidMain {
            dependencies {
                implementation(libs.ktor.client.okhttp)
                implementation(libs.okhttp3.logging.interceptor)
            }
        }

        iosMain {
            dependencies {
                implementation(libs.ktor.client.ios)
            }
        }
    }
}
