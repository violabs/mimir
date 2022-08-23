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
  implementation("io.github.microutils:kotlin-logging-jvm:2.1.23")

  testImplementation("org.springframework.boot:spring-boot-starter-test")
}
repositories {
  mavenCentral()
}

tasks.withType<Test> {
  useJUnitPlatform()
}