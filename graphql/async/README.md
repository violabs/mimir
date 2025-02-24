# GraphQL Async Module

This module demonstrates asynchronous operations with GraphQL in a Spring Boot application using Kotlin coroutines.

## Features
- Asynchronous GraphQL query execution
- Kotlin coroutines integration
- Non-blocking database operations
- Subscription support

## Setup

### Prerequisites
- Kotlin 2.x
- Spring Boot 3.x
- GraphQL dependencies configured in root project

### Configuration
1. Include the module in your build:
```kotlin
implementation(project(":graphql:async"))
```

2. Enable async support in your application:
```kotlin
@SpringBootApplication
@EnableGraphQLAsyncOperations
class YourApplication
```

## Usage Examples

### Basic Async Query
```kotlin
@QueryMapping
suspend fun asyncData(): SomeType = coroutineScope {
    // Async operation here
}
```

### Subscription Example
```kotlin
@SubscriptionMapping
fun dataUpdates(): Flow<UpdateType> = flow {
    // Subscription implementation
}
```

## Testing
Run the tests:
```bash
./gradlew :graphql:async:test
```

## Additional Resources
- [Spring GraphQL Documentation](https://docs.spring.io/spring-graphql/docs/current/reference/html/)
- [Kotlin Coroutines Documentation](https://kotlinlang.org/docs/coroutines-overview.html)
