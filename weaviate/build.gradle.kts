
plugins {
    id("org.springframework.boot")

    kotlin("jvm")
    kotlin("plugin.spring")
    id("com.avast.gradle.docker-compose") version "0.17.6"
}

repositories {
    mavenCentral()
    maven { url = uri("https://repo.spring.io/milestone") }
}

dependencies {
    implementation(project(":core"))

    implementation(kotlin("stdlib-jdk8"))
    implementation(kotlin("reflect"))
    implementation("org.springframework.boot:spring-boot-starter-web")
    annotationProcessor("org.springframework.boot:spring-boot-autoconfigure-processor")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("io.github.microutils:kotlin-logging-jvm:3.0.5")
    implementation("io.springboot.ai:spring-ai-ollama-spring-boot-starter:1.0.0")
    implementation("io.springboot.ai:spring-ai-weaviate-store:1.0.0")
    implementation("org.springdoc:springdoc-openapi-ui:1.8.0")
	implementation("org.springdoc:springdoc-openapi-webmvc-core:1.8.0")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

dockerCompose {
    useComposeFiles.set(listOf("docker-compose.test.yml"))
}

tasks.withType<Test> {
    dockerCompose.isRequiredBy(this)

    systemProperty("spring.profiles.active", "test")

    useJUnitPlatform()
}