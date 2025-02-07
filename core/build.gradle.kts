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

// Configure Kover specifically for this module
kover {
    // Enable Kover for this module
    enable()
    
    // Configure instrumentation
    instrumentation {
        excludeTasks.clear() // Clear any default excludes
    }
    
    // Configure reporting
    reporting {
        // XML reports configuration
        xml {
            onCheck.set(false) // Don't generate on every check
            setReportFile(layout.buildDirectory.file("reports/kover/coverage.xml"))
        }
    }
}
