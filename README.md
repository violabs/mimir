# Mimir - Spring Boot Integration Examples 🚀

A comprehensive collection of sample projects demonstrating how to integrate various external services with Spring Boot.

## Quick Start ⚡
### Prerequisites
- Docker Desktop 🐳
- Java 21+ ☕
- Kotlin 2.x 💪
- IDE (IntelliJ IDEA recommended) 💻

### Basic Setup 🔧
1. Clone the repository
2. Run `./gradlew build`
3. Navigate to specific module
4. Follow module-specific README

## Project Structure 📁
```
mimir/
├── core/                 # Shared utilities and base configurations
├── database/            # Database integration examples
│   ├── postgres/        # PostgreSQL integration
│   └── mysql/          # MySQL integration
├── messaging/           # Message queue examples
│   └── simple-kafka/   # Basic Kafka setup
├── testing/             # Testing examples
│   └── selenium/       # Selenium integration
└── vector/              # Vector database examples
    └── weaviate/       # Weaviate integration
```

## Available Integrations 🔌

### Core Features 🛠️
- [Core Module Documentation](/docs/core/README.md)
- Common utilities and configurations
- Shared test helpers
- Base implementations

### Databases 💾
- [PostgreSQL Integration](/docs/databases/postgresql.md) 
- [MySQL Integration](/docs/databases/mysql.md)
- [View Database Docs](/docs/databases/README.md)

### Message Queues 📨
- [Simple Kafka Setup](/docs/messaging/kafka-simple.md)
- [View Messaging Docs](/docs/messaging/README.md)

### Testing 🧪
- [Testing Overview](/docs/testing/README.md)
- [Selenium Integration](/docs/testing/selenium.md)
- Unit Testing Guidelines
- Integration Testing Patterns

### Vector Databases 🧬
- [Weaviate Integration](/docs/vector-databases/weaviate.md)
- Vector Search Examples
- Embedding Generation

## Contributing 🤝
See our [contribution guide](/docs/contributing.md) for:
- Adding new examples
- Code style guidelines
- PR process

## Development 👩‍💻

### Building and Testing
```bash
# Build all modules
./gradlew build

# Run all tests
./gradlew test

# Build specific module
./gradlew :database:postgres:build

# Test specific module
./gradlew :database:postgres:test
```

### Docker Integration 🐳
Each module contains its own docker-compose.yml for required services.
See [Docker Configuration Guide](/docs/core/docker-configuration.md).

## Resources 📚
- [Complete Documentation](/docs/README.md)
- [Spring Boot Documentation](https://docs.spring.io/spring-boot/docs/current/reference/html/)
- [Kotlin Documentation](https://kotlinlang.org/docs/home.html)
- [Docker Compose Documentation](https://docs.docker.com/compose/)

## License 📄
This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details