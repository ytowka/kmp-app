plugins {
    id("module-setup")
    alias(libs.plugins.kotlinSerialization)
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                implementation(libs.kotlinx.serialization.core)
                implementation(libs.kotlinx.serialization.json)
                implementation(libs.kotlin.stdlib)
            }
        }
    }
}