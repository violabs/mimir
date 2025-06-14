plugins {
    id("org.springframework.boot")

    kotlin("jvm")
    kotlin("plugin.spring")
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
    implementation("io.github.microutils:kotlin-logging:4.0.0-beta-2")
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
    //    implementation("org.springframework.boot:spring-boot-starter-data-mongodb-reactive")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.kafka:spring-kafka-test")
}

repositories {
    mavenCentral()
}

tasks.withType<Test> {
    systemProperty("spring.profiles.active", "test")

    useJUnitPlatform()
}