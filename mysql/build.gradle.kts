
plugins {
    id("org.springframework.boot")

    kotlin("jvm")
    kotlin("plugin.spring")
    id("com.avast.gradle.docker-compose") version "0.17.6"
}

dependencies {
    implementation(project(":core"))
    implementation(project(":core:springJpaCore"))

    implementation(kotlin("stdlib-jdk8"))
    implementation(kotlin("reflect"))
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
    annotationProcessor("org.springframework.boot:spring-boot-autoconfigure-processor")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("io.github.microutils:kotlin-logging-jvm:3.0.5")

    runtimeOnly("com.mysql:mysql-connector-j:8.3.0")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

repositories {
    mavenCentral()
}

dockerCompose {
    useComposeFiles.set(listOf("docker-compose.test.yml"))
}

tasks.withType<Test> {
    dockerCompose.isRequiredBy(this)

    systemProperty("spring.profiles.active", "test")

    useJUnitPlatform()
}