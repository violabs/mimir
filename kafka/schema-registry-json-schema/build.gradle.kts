plugins {
    kotlin("jvm")
    kotlin("plugin.spring")
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    id("com.github.davidmc24.gradle.plugin.avro") version "1.9.1"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
    maven {
        url = uri("https://packages.confluent.io/maven/")
    }
}

ext {
    set("jackson.version", "2.15.2") // Use latest stable
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.kafka:spring-kafka")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")

    implementation("io.confluent:kafka-schema-registry-client:8.0.0")

    // Confluent Schema Registry and JSON Schema
    implementation("io.confluent:kafka-json-schema-serializer:8.0.0")
    implementation("io.confluent:kafka-streams-json-schema-serde:8.0.0")


    // JSON Schema
    implementation("com.networknt:json-schema-validator:1.5.8")
    implementation("com.github.victools:jsonschema-generator:4.38.0")

//    implementation("com.fasterxml.jackson.core:jackson-databind:2.19.1")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.kafka:spring-kafka-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testImplementation("org.apache.commons:commons-lang3:3.18.0")
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

avro {
    stringType = "String"
    fieldVisibility = "private"
    outputCharacterEncoding = "UTF-8"
    isCreateOptionalGetters = false
}

dockerCompose {
    useComposeFiles.set(listOf("./docker/docker-compose.yml"))
    composeAdditionalArgs.add("--profile=test")
    isRequiredBy(tasks.test)
}