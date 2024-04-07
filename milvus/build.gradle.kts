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
    annotationProcessor("org.springframework.boot:spring-boot-autoconfigure-processor")
    implementation("org.springframework.boot:spring-boot-starter-logging") {
        exclude(group = "org.apache.logging.log4j", module = "log4j-slf4j-impl")
    }
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("io.github.microutils:kotlin-logging-jvm:3.0.5")
    implementation("io.milvus:milvus-sdk-java:2.3.5") {
        exclude(group = "com.squareup.okio", module = "okio")
        exclude(group = "commons-beanutils", module = "commons-beanutils")
    }

    implementation("com.squareup.okio:okio:3.9.0")
    implementation("commons-beanutils:commons-beanutils:1.9.4")

    testImplementation("com.github.violabs:wesley:1.1.2")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

class OverrideDependency(val artifact: String, val version: String)

repositories {
    mavenCentral()
    maven { url = uri("https://jitpack.io") }
}

tasks.withType<Test> {
    systemProperty("spring.profiles.active", "test")

    useJUnitPlatform()
}