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
  implementation("io.github.microutils:kotlin-logging-jvm:2.1.20")

  testImplementation("org.springframework.boot:spring-boot-starter-test")
  testImplementation("org.springframework.kafka:spring-kafka-test")
  testImplementation("org.testcontainers:kafka:1.17.3")
}

tasks.withType<Test> {
  useJUnitPlatform()
}