plugins {
    kotlin("jvm") version "2.0.20"
    `kotlin-dsl`
    kotlin("plugin.serialization") version "2.0.20"
}

buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath(kotlin("gradle-plugin", version = "2.0.20"))
    }
}

repositories {
    // Add any required repositories
    mavenCentral()
}

dependencies {
    val ktorClientVersion = "3.0.1"
    val kotlinJacksonVersion = "2.17.1"
    // Add any required dependencies
    implementation("org.springframework:spring-context:6.1.14")
    implementation("io.ktor:ktor-client-cio:$ktorClientVersion") // or another engine like ktor-client-apache, ktor-client-okhttp, etc.
    implementation("io.ktor:ktor-client-json:$ktorClientVersion")
    implementation("io.ktor:ktor-client-serialization:$ktorClientVersion")

    implementation("io.ktor:ktor-client-core:$ktorClientVersion")
    implementation("io.ktor:ktor-client-okhttp:$ktorClientVersion") // Using OkHttp for better security
    implementation("io.ktor:ktor-client-content-negotiation:$ktorClientVersion")
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorClientVersion")
    implementation("io.ktor:ktor-client-logging:$ktorClientVersion")

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:$kotlinJacksonVersion")
    annotationProcessor("com.fasterxml.jackson.module:jackson-module-kotlin:$kotlinJacksonVersion")
}

gradlePlugin {
    plugins {
        create("ollamaTestStartup") {
            id = "io.violabs.plugins.ai.ollama-test-startup"
            implementationClass = "io.violabs.mimir.buildsrc.ai.ollamaTestStartup.OllamaTestStartupPlugin"
        }
    }
}

kotlin {
    jvmToolchain(17)
}