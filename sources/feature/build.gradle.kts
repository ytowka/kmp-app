plugins {
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.googleService)
    id("module-setup")
    id("shared-koin")
}

kotlin {
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
                implementation(project.dependencies.platform(libs.firebase.bom))
                implementation(libs.firebase.crashlytics)
                implementation(libs.firebase.analytics)
            }
        }
    }
}