import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.7.2" apply false
    id("io.spring.dependency-management") version "1.0.12.RELEASE" apply false
    kotlin("jvm") version "1.7.0" apply false
    kotlin("plugin.spring") version "1.7.0" apply false
}

buildscript {
    repositories {
        mavenCentral()
    }
}

allprojects {
    group = "io.violabs"
    version = "1.0-SNAPSHOT"

    tasks.withType<JavaCompile> {
        sourceCompatibility = JavaVersion.VERSION_17.majorVersion
        targetCompatibility = JavaVersion.VERSION_17.majorVersion
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = JavaVersion.VERSION_17.majorVersion
        }
    }
}

subprojects {
    repositories {
        mavenCentral()
    }

    apply {
        plugin("io.spring.dependency-management")
    }
}