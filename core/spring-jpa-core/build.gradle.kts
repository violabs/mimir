plugins {
  id("org.springframework.boot")

  kotlin("jvm")
  kotlin("plugin.spring")
}

dependencies {
  implementation(project(":core"))

  implementation(kotlin("stdlib-jdk8"))
  implementation(kotlin("reflect"))

  implementation("org.springframework.boot:spring-boot-starter-data-jpa")
  implementation("org.springframework.boot:spring-boot-starter-web")
}

repositories {
  mavenCentral()
}