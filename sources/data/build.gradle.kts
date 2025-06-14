plugins {
    id("module-setup")
    id("shared-koin")
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                implementation(libs.kotlin.stdlib)
                implementation(libs.androidx.datastore.preferences)
            }
        }
    }
}