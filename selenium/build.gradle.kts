plugins {
    id("org.springframework.boot")

    kotlin("jvm")
    kotlin("plugin.spring")
}

group = "io.violabs"
version = "0.0.1-SNAPSHOT"

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.seleniumhq.selenium:selenium-java:4.20.0")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}