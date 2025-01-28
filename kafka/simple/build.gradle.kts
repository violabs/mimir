plugins {
    id("org.springframework.boot")

    kotlin("jvm")
    kotlin("plugin.spring")
    id("com.avast.gradle.docker-compose") version "0.17.6"
}

dependencies {
    implementation(project(":core"))

    implementation(kotlin("stdlib-jdk8"))
    implementation(kotlin("reflect"))
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-web")
    annotationProcessor("org.springframework.boot:spring-boot-autoconfigure-processor")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.springframework.kafka:spring-kafka")
    implementation("io.github.microutils:kotlin-logging-jvm:3.0.5")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.kafka:spring-kafka-test")
}

repositories {
    mavenCentral()
}

dockerCompose {
  useComposeFiles.set(listOf("./docker/docker-compose.yml"))
}

tasks.withType<Test> {
  dockerCompose.isRequiredBy(this)

  systemProperty("spring.profiles.active", "test")

  useJUnitPlatform()
}