# Quick Start Guide

## Prerequisites
- Docker Desktop
- Java 21+
- Kotlin 1.9.x
- IDE (IntelliJ IDEA recommended)

## Initial Setup
1. Clone the repository:
```bash
git clone https://github.com/violabs/mimir.git
cd mimir
```

2. Build the project:
```bash
./gradlew build
```

3. Choose an integration:
- [PostgreSQL](databases/postgresql.md)
- [Simple Kafka](messaging/kafka-simple.md)
- [Selenium](testing/selenium.md)

## Project Structure
```
mimir/
├── core/           # Shared utilities
├── first/          # Basic example
├── postgres/       # PostgreSQL integration
├── mysql/          # MySQL integration
├── simpleKafka/    # Basic Kafka setup
└── selenium/       # Selenium testing
```

## Running Tests
```bash
./gradlew test
```

## Docker Integration
Each module includes:
- docker-compose.yml
- Dockerfile (if needed)
- Container configuration