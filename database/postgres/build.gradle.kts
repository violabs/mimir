
plugins {
    id("org.springframework.boot")

    kotlin("jvm")
    kotlin("plugin.spring")
}

dependencies {
    implementation(project(":core"))
    implementation(project(":core:spring-jpa-core"))

    implementation(kotlin("stdlib-jdk8"))
    implementation(kotlin("reflect"))
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
    annotationProcessor("org.springframework.boot:spring-boot-autoconfigure-processor")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("io.github.microutils:kotlin-logging:4.0.0-beta-2")

    runtimeOnly("org.postgresql:postgresql")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

repositories {
    mavenCentral()
}

dockerCompose {
    useComposeFiles.set(listOf("./docker/docker-compose.yml"))
    composeAdditionalArgs.add("--profile=test")
    isRequiredBy(tasks.test)
}

tasks.withType<Test> {
    systemProperty("spring.profiles.active", "test")

    useJUnitPlatform()
}