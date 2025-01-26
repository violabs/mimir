# Mimir - Spring Boot Integration Examples

A comprehensive collection of sample projects demonstrating how to integrate various external services with Spring Boot.

## Quick Start
### Prerequisites
- Docker Desktop
- Java 21+
- Kotlin 1.9.x

### Basic Setup
1. Clone the repository
2. Run `./gradlew build`
3. Navigate to specific module
4. Follow module-specific README

## Project Structure
- `core/` - Shared utilities and base configurations
- `first/` - Your first Spring Boot integration example
- Each additional folder represents a specific integration example

## Available Integrations

### Databases
- [PostgreSQL](/docs/databases/postgresql.md)
- [MySQL](/docs/databases/mysql.md)
- View [planned database integrations](/docs/databases/README.md)

### Message Queues
- [Simple Kafka Setup](/docs/messaging/kafka-simple.md)
- View [planned messaging integrations](/docs/messaging/README.md)

### Testing
- [Selenium Integration](/docs/testing/selenium.md)

### Vector Databases
- [Weaviate](/docs/vector-databases/weaviate.md)

## Contributing
See our [contribution guide](/docs/contributing.md) for:
- Adding new examples
- Code style guidelines
- PR process

## Development
### Building
```bash
./gradlew build
```

### Testing
```bash
./gradlew test
```

### Docker Environment
Each module contains its own docker-compose.yml for required services.

## Resources
- [Gradle Docker Compose Plugin](https://github.com/avast/gradle-docker-compose-plugin)
- [Spring Boot Documentation](https://docs.spring.io/spring-boot/docs/current/reference/html/)
- [Project Documentation](/docs/README.md)

[View Full Documentation](/docs/README.md)