
plugins {
    id("org.springframework.boot")

    kotlin("jvm")
    kotlin("plugin.spring")
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
    implementation("io.github.microutils:kotlin-logging-jvm:3.0.4")

    runtimeOnly("com.mysql:mysql-connector-j:8.0.31")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
}
repositories {
    mavenCentral()
}

tasks.withType<Test> {
    systemProperty("spring.profiles.active", "test")

    useJUnitPlatform()
}
// Couldn't get it working - not the focus
//fun startDocker() {
//    exec {
//        executable("docker compose -f docker-compose.yml up")
//    }
//}
//
//tasks.register("docker") {
//
//    startDocker()
//}
//
//tasks.named("build") {
//    this.dependsOn("docker")
//}