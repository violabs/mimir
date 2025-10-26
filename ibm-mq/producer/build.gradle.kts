
plugins {
    id("org.springframework.boot")

    kotlin("jvm")
    kotlin("plugin.spring")
}

dependencies {
    implementation(project(":core"))

    implementation(kotlin("stdlib-jdk8"))
    implementation(kotlin("reflect"))
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.ibm.mq:com.ibm.mq.jakarta.client:9.4.1.0")
    implementation("org.springframework:spring-jms")
    implementation("jakarta.jms:jakarta.jms-api")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("io.github.microutils:kotlin-logging-jvm:3.0.5")

    annotationProcessor("org.springframework.boot:spring-boot-autoconfigure-processor")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

repositories {
    mavenCentral()
}

dockerCompose {
    useComposeFiles.set(listOf("../docker/docker-compose.yml"))
    composeAdditionalArgs.add("--profile=test")
    isRequiredBy(tasks.test)
}

tasks.withType<Test> {
    systemProperty("spring.profiles.active", "test")

    useJUnitPlatform()
}
