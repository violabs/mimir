# Core Module Overview 🛠️

The core module provides shared utilities and base configurations used across all integration examples.

## Features 📋

### Base Configurations
- Spring Boot application configuration
- Common properties management
- Security settings
- Logging configuration

### Shared Utilities
- Error handling
- Common extensions
- Utility functions
- Testing helpers

### Common Components
- Base entity classes
- Generic repositories
- Shared service patterns
- Common controllers

## Usage 💡

### Dependency Management
Add to your module's `build.gradle.kts`:
```kotlin
dependencies {
    implementation(project(":core"))
}
```

### Configuration
```kotlin
@Configuration
class YourConfig : BaseConfig() {
    // Your module-specific configuration
}
```

### Error Handling
```kotlin
class YourService {
    fun doSomething() = handleErrors {
        // Your code here
    }
}
```

## Best Practices 🎯
1. Use core utilities when available
2. Extend base classes for consistency
3. Follow established patterns
4. Contribute improvements back to core

## Examples 📝
- [Error Handling](error-handling.md)
- [Configuration](configuration.md)
- [Utilities](utilities.md)
- [Testing](../testing/README.md)