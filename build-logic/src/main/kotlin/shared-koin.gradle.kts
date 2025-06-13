import org.gradle.kotlin.dsl.dependencies
import com.google.devtools.ksp.gradle.KspAATask
import org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask


plugins {
    kotlin("multiplatform")
    id("com.google.devtools.ksp")
}

val libs = the<VersionCatalogsExtension>().named("libs")

fun VersionCatalog.koin(): Any = findLibrary("koin.compiler").get()

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
    add("kspCommonMainMetadata", libs.koin())
    configurations.forEach {
        if(it.name.contains("ksp")) {
            add(it.name, libs.koin())
        }
    }
}

ksp {
    arg("KOIN_DEFAULT_MODULE","false")
}


project.configurations.whenObjectAdded {
    if (name.contains("ksp")) {
        project.dependencies.add(name, libs.koin())
    }
}


afterEvaluate {
    kotlin {
        sourceSets.named("androidMain") {
            kotlin.srcDir("build/generated/ksp/android/androidMain/kotlin")
        }
    }

    tasks.withType<KotlinCompilationTask<*>>().configureEach {
        if (name != "kspCommonMainKotlinMetadata") {
            dependsOn("kspCommonMainKotlinMetadata")
        }
    }
    tasks.withType<KspAATask>().configureEach {
        if (name != "kspCommonMainKotlinMetadata") {
            dependsOn("kspCommonMainKotlinMetadata")
        }
    }
}