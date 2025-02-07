import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "3.2.4" apply false
    id("io.spring.dependency-management") version "1.1.4" apply false
    kotlin("jvm") version "2.0.20" apply false
    kotlin("plugin.spring") version "2.0.20" apply false
    id("com.avast.gradle.docker-compose") version "0.17.12"
    id("org.jetbrains.kotlinx.kover") version "0.7.6"
}

buildscript {
    repositories {
        mavenCentral()
        maven { url = uri("https://repo.spring.io/milestone") }
    }
}

allprojects {
    group = "io.violabs"

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
    repositories {
        mavenCentral()
    }

    apply {
        plugin("io.spring.dependency-management")
        plugin("com.avast.gradle.docker-compose")
        plugin("org.jetbrains.kotlinx.kover")
    }
    
    // Configure Kover for each subproject
    extensions.configure<kotlinx.kover.gradle.plugin.dsl.KoverProjectExtension> {
        // Disable default reports (we'll set up module-specific ones)
        disable()
    }
}

// Task for aggregated coverage report
tasks.register("koverMergedReport") {
    group = "verification"
    description = "Generates merged coverage report for all modules"
    
    dependsOn(subprojects.map { it.tasks.named("koverXmlReport") })
}