import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.io.ByteArrayOutputStream

plugins {
    id("org.springframework.boot") version "3.2.4" apply false
    id("io.spring.dependency-management") version "1.1.4" apply false
    kotlin("jvm") version "2.0.20" apply false
    kotlin("plugin.spring") version "2.0.20" apply false
    id("com.avast.gradle.docker-compose") version "0.17.12"
    id("org.jetbrains.dokka") version "1.9.20"
    id("org.jetbrains.kotlinx.kover") version "0.7.6"
}

buildscript {
    repositories {
        mavenCentral()
        maven { url = uri("https://repo.spring.io/milestone") }
        maven { url = uri("https://www.jetbrains.com/intellij-repository/releases") }
    }
}

allprojects {
    group = "io.violabs"
    version = "0.0.1"

    sharedRepositories()

    tasks.withType<JavaCompile> {
        sourceCompatibility = JavaVersion.VERSION_17.majorVersion
        targetCompatibility = JavaVersion.VERSION_17.majorVersion
    }

    tasks.withType<KotlinCompile> {
        compilerOptions.apply {
            jvmTarget.set(JvmTarget.JVM_17)
            freeCompilerArgs.set(listOf("-Xjsr305=strict"))
        }
    }
}

subprojects {
    sharedRepositories()

    apply {
        plugin("io.spring.dependency-management")
        plugin("com.avast.gradle.docker-compose")
        plugin("org.jetbrains.kotlin.jvm")
        plugin("org.jetbrains.dokka")
        plugin("org.jetbrains.kotlinx.kover")
    }
}

fun Project.sharedRepositories() {
    repositories {
        mavenCentral()
        maven { url = uri("https://repo.spring.io/milestone") }
        maven { url = uri("https://www.jetbrains.com/intellij-repository/releases") }
    }
}

// Task for aggregated coverage report
tasks.register("koverMergedReport") {
    group = "verification"
    description = "Generates merged coverage report for all modules"

    dependsOn(subprojects.map { it.tasks.named("koverXmlReport") })
}

tasks.register("printModules") {
    doLast {
        val modules = rootProject.subprojects.map { it.path.removePrefix(":") }
        modules.forEach { println(it) }
    }
}


tasks.register("detectChangedModules") {
    doLast {
        val changedFiles = System.getenv("CHANGED_FILES")
            ?.split(" ")
            ?.filter { it.isNotBlank() }
            ?: run {
                val outputStream = ByteArrayOutputStream()
                exec {
                    commandLine("git", "diff", "--name-only", "origin/main...HEAD")
                    standardOutput = outputStream
                }

                outputStream
                    .toString()
                    .trim()
                    .split("\n")
                    .filter { it.isNotBlank() }
            }

        if (changedFiles.isEmpty()) {
            println("""MATRIX={"modulePaths":[],"gradleModules":[]}""")  // ✅ Always return valid JSON
            return@doLast
        }

        // Convert Gradle's ":"-based module names to filesystem paths
        val allModules = rootProject
            .subprojects
            .map { it.path.removePrefix(":").replace(":", "/") }
            .sortedByDescending { it.length }
            .toSet()

        // Identify affected modules, considering submodules properly
        val changedModules = changedFiles
            .mapNotNull { file ->
                allModules.find { module ->
                    file.startsWith("$module/") || file.startsWith("$module\\")
                }
            }
            .toSet()

        val gradleModules = changedModules
            .map { module -> ":${module.replace("/", ":")}" } // Convert back to Gradle format

        val changedModulePathJson = changedModules.joinToString(prefix = "[\"", separator = "\", \"", postfix = "\"]")
        val gradleModuleJson = gradleModules.joinToString(prefix = "[\"", separator = "\", \"", postfix = "\"]")

        // ✅ Ensure JSON formatting is correct
        println("""MATRIX={"modulePaths":$changedModulePathJson,"gradleModules":$gradleModuleJson}""")
    }
}

