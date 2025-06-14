plugins {
    `kotlin-dsl`
}

dependencies {
    implementation(kotlin("gradle-plugin"))
    implementation(libs.com.google.devtools.ksp.gradle.plugin)
    implementation(libs.org.jetbrains.kotlin.multiplatform.gradle.plugin)
    implementation(libs.com.android.kotlin.multiplatform.gradle.plugin)
}