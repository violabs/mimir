plugins {
    id("org.springframework.boot")
    kotlin("jvm")
    kotlin("plugin.spring")
}

dependencies {
    // Core Spring Boot dependencies
    implementation(kotlin("stdlib-jdk8"))
    implementation(kotlin("reflect"))
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-data-redis")
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
    implementation("org.springframework.kafka:spring-kafka")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("io.github.microutils:kotlin-logging:4.0.0-beta-2")

    // Database drivers
    runtimeOnly("org.postgresql:postgresql")
    runtimeOnly("mysql:mysql-connector-java:8.0.33")
    runtimeOnly("org.mariadb.jdbc:mariadb-java-client")

    // Testcontainers dependencies
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
    }
    testImplementation("org.testcontainers:testcontainers:1.19.7")
    testImplementation("org.testcontainers:junit-jupiter:1.19.7")

    // Testcontainers modules for specific technologies
    testImplementation("org.testcontainers:postgresql:1.19.7")
    testImplementation("org.testcontainers:mysql:1.19.7")
    testImplementation("org.testcontainers:mariadb:1.19.7")
    testImplementation("org.testcontainers:mongodb:1.19.7")
    testImplementation("org.testcontainers:kafka:1.19.7")
    testImplementation("org.testcontainers:elasticsearch:1.19.7")
    testImplementation("org.testcontainers:localstack:1.19.7")
    testImplementation("org.testcontainers:rabbitmq:1.19.7")
    testImplementation("org.testcontainers:cassandra:1.19.7")
    testImplementation("org.testcontainers:clickhouse:1.19.7")
    testImplementation("org.testcontainers:couchbase:1.19.7")
    testImplementation("org.testcontainers:dynalite:1.19.7")
    testImplementation("org.testcontainers:influxdb:1.19.7")
    testImplementation("org.testcontainers:mssqlserver:1.19.7")
    testImplementation("org.testcontainers:neo4j:1.19.7")
    testImplementation("org.testcontainers:oracle-xe:1.19.7")
    testImplementation("org.testcontainers:orientdb:1.19.7")
    testImplementation("org.testcontainers:pulsar:1.19.7")
    testImplementation("org.testcontainers:r2dbc:1.19.7")
    testImplementation("org.testcontainers:redpanda:1.19.7")
    testImplementation("org.testcontainers:solr:1.19.7")
    testImplementation("org.testcontainers:tidb:1.19.7")
    testImplementation("org.testcontainers:toxiproxy:1.19.7")
    testImplementation("org.testcontainers:vault:1.19.7")

    // Additional testing utilities
    testImplementation("io.mockk:mockk:1.13.10")
    testImplementation("com.ninja-squad:springmockk:4.0.2")
    testImplementation("org.awaitility:awaitility-kotlin:4.2.1")
    testImplementation("io.kotest:kotest-runner-junit5:5.8.1")
    testImplementation("io.kotest:kotest-assertions-core:5.8.1")
    testImplementation("io.kotest.extensions:kotest-extensions-testcontainers:2.0.2")

    // RestAssured for API testing
    testImplementation("io.rest-assured:rest-assured:5.4.0")
    testImplementation("io.rest-assured:kotlin-extensions:5.4.0")
    testImplementation("io.rest-assured:spring-mock-mvc:5.4.0")

    // Spring Boot Test Containers integration
    testImplementation("org.springframework.boot:spring-boot-testcontainers")

    // Additional Spring testing support
    testImplementation("org.springframework.kafka:spring-kafka-test")
    testImplementation("org.springframework.amqp:spring-rabbit-test")

    // AWS SDK v2 for LocalStack testing
    testImplementation("software.amazon.awssdk:aws-sdk-java:2.25.11")
    testImplementation("software.amazon.awssdk:s3:2.25.11")
    testImplementation("software.amazon.awssdk:dynamodb:2.25.11")
    testImplementation("software.amazon.awssdk:sqs:2.25.11")
    testImplementation("software.amazon.awssdk:sns:2.25.11")

    // Elasticsearch client for testing
    testImplementation("co.elastic.clients:elasticsearch-java:8.12.2")
}

repositories {
    mavenCentral()
    maven { url = uri("https://repo.spring.io/milestone") }
}

// Docker Compose configuration commented out - using Testcontainers instead
// dockerCompose {
//     useComposeFiles.set(listOf("./docker/docker-compose.yml"))
//     composeAdditionalArgs.add("--profile=test")
//     isRequiredBy(tasks.test)
// }

// Test configuration
tasks.withType<Test> {
    useJUnitPlatform()
    systemProperty("spring.profiles.active", "test")

    // Testcontainers configuration
    environment("TESTCONTAINERS_REUSE_ENABLE", "true")
    environment("TESTCONTAINERS_RYUK_DISABLED", "false")

    // JVM settings for better container performance
    jvmArgs = listOf(
        "-Xmx2048m",
        "-XX:+UseG1GC",
        "-XX:MaxGCPauseMillis=100"
    )

    // Test reporting
    testLogging {
        events("PASSED", "FAILED", "SKIPPED")
        showExceptions = true
        showStackTraces = true
        showCauses = true
        exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
    }

    // Parallel test execution configuration
    maxParallelForks = (Runtime.getRuntime().availableProcessors() / 2).takeIf { it > 0 } ?: 1
}

// Task to run only unit tests (excluding integration tests)
tasks.register<Test>("unitTest") {
    useJUnitPlatform {
        excludeTags("integration", "testcontainers")
    }
    group = "verification"
    description = "Runs only unit tests"
}

// Task to run only integration tests with Testcontainers
tasks.register<Test>("integrationTest") {
    useJUnitPlatform {
        includeTags("integration", "testcontainers")
    }
    group = "verification"
    description = "Runs only integration tests with Testcontainers"
    shouldRunAfter(tasks.named("unitTest"))
}

// Configure Spring Boot plugin
springBoot {
    buildInfo()
}

// Configure bootJar task
tasks.bootJar {
    enabled = true
    archiveClassifier.set("boot")

    manifest {
        attributes(
            "Implementation-Title" to project.name,
            "Implementation-Version" to project.version
        )
    }
}

// Configure jar task
tasks.jar {
    enabled = true
    archiveClassifier.set("")
}

// Task for cleaning up Docker containers
tasks.register("cleanContainers") {
    group = "docker"
    description = "Removes all test containers"
    doLast {
        exec {
            commandLine("docker", "ps", "-aq", "--filter", "label=org.testcontainers=true")
            standardOutput = System.out
        }.takeIf { it.exitValue == 0 }?.let {
            exec {
                commandLine("docker", "rm", "-f", "$(docker ps -aq --filter label=org.testcontainers=true)")
                standardOutput = System.out
                isIgnoreExitValue = true
            }
        }
    }
}