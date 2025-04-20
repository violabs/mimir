plugins {
    id("org.springframework.boot")

    kotlin("jvm")
    kotlin("plugin.spring")

    id("io.violabs.plugins.ai.ollama-test-startup")
    id("io.violabs.plugins.docker.helper")
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
    composeAdditionalArgs.add("--profile=test-amd")
    composeAdditionalArgs.add("--profile=ollama")
    isRequiredBy(tasks.test)
}

val debugEnabled = if (project.hasProperty("debug")) {
    // Get the property value as a string and convert it to a boolean
    // project.property("debug") returns Any?, so we convert it to String
    project.property("debug").toString().toBoolean()
} else {
    // Default value if -Pdebug is not provided
    false
}

tasks.named("composeUp") {
//    if (debugEnabled)
    finalizedBy(tasks.checkRunningContainers)

    finalizedBy(tasks.pullOnStartup)
}

ollamaTestStartup {
    withDefault = true
    models("qwen2.5:1.5b")
}
