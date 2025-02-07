plugins {
  kotlin("jvm")
}

dependencies {
  implementation(kotlin("stdlib-jdk8"))
  implementation(kotlin("reflect"))
}

tasks.test {
  filter {
    // Excludes integration tests by default
    excludeTestsMatching("*IT")
    excludeTestsMatching("*IntegrationTest")
  }
}

// Custom task for running integration tests
tasks.register<Test>("integrationTest") {
  group = "verification"
  filter {
    includeTestsMatching("*IT")
    includeTestsMatching("*IntegrationTest")
  }
}
