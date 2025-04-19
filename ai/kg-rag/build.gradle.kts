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
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("com.fasterxml.jackson.core:jackson-databind")
    implementation("org.springframework.boot:spring-boot-starter-data-neo4j")

    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springdoc:springdoc-openapi-starter-webflux-ui:2.5.0")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
    annotationProcessor("org.springframework.boot:spring-boot-autoconfigure-processor")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("io.github.microutils:kotlin-logging:4.0.0-beta-2")

    implementation("org.springframework.ai:spring-ai-weaviate-store")
    implementation("org.springframework.ai:spring-ai-starter-model-ollama")
    implementation(platform("org.springframework.ai:spring-ai-bom:1.0.0-SNAPSHOT"))

    testImplementation(project(":core:testing"))
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<Test> {
    systemProperty("spring.profiles.active", "test")

    useJUnitPlatform()
}

dockerCompose {
    useComposeFiles.set(listOf("./docker/docker-compose.yml"))
    composeAdditionalArgs.add("--profile=test")
    isRequiredBy(tasks.test)
}

tasks.named("composeUp") {
    finalizedBy(tasks.pullOnStartup)
}
