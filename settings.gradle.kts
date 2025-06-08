enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}

fun includeModule(name: String) {
    include(":$name")
    project(":$name").projectDir = file("sources/$name")
}

rootProject.name = "KMPapp"

includeBuild("build-logic")
include(":androidApp")

includeModule("shared")
includeModule("core")
includeModule("network")
includeModule("api")
includeModule("data")
