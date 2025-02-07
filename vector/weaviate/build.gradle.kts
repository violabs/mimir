
plugins {
    id("org.springframework.boot")

    kotlin("jvm")
    kotlin("plugin.spring")

    id("io.violabs.plugins.ai.ollama-test-startup")
}

repositories {
    mavenCentral()
    maven { url = uri("https://repo.spring.io/milestone") }
    maven { url = uri("https://repo.spring.io/snapshot") }
}

dependencies {
    implementation(project(":core:common"))

    implementation(kotlin("stdlib-jdk8"))
    implementation(kotlin("reflect"))
    implementation("org.springframework.boot:spring-boot-starter-web")
    annotationProcessor("org.springframework.boot:spring-boot-autoconfigure-processor")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.springframework.ai:spring-ai-ollama-spring-boot-starter")
    implementation("org.springframework.ai:spring-ai-weaviate-store")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.3.0")

    implementation(platform("org.springframework.ai:spring-ai-bom:1.0.0-SNAPSHOT"))

    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

dockerCompose {
    val testDocker = nested("test")
    testDocker.useComposeFiles.set(listOf("./docker/docker-compose.yml"))
    testDocker.composeAdditionalArgs.add("--profile=test")
    testDocker.isRequiredBy(tasks.test)

    val e2eDocker = nested("e2e")
    e2eDocker.useComposeFiles.set(listOf("./docker/docker-compose.yml"))
    e2eDocker.composeAdditionalArgs.add("--profile=e2e")
}

tasks.withType<Test> {
    systemProperty("spring.profiles.active", "test")

    useJUnitPlatform()
}

tasks.composeUp {
    finalizedBy(tasks.pullOnStartup)
}