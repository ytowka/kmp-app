import org.gradle.kotlin.dsl.dependencies
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("multiplatform")
    id("com.google.devtools.ksp")
}

val libs = the<VersionCatalogsExtension>().named("libs")

kotlin {
    sourceSets.named("commonMain") {
        kotlin.srcDir("build/generated/ksp/metadata/commonMain/kotlin")
        dependencies {
            api(libs.findLibrary("koin.core").get())
            api(libs.findLibrary("koin.annotations").get())
        }
    }
}

dependencies {
    add("kspCommonMainMetadata", libs.findLibrary("koin.compiler").get())
}


tasks.withType<KotlinCompile>().configureEach {
    if (name != "kspCommonMainKotlinMetadata") {
        dependsOn("kspCommonMainKotlinMetadata")
    }
}
