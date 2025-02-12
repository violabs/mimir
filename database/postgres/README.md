# PostgreSQL Module

This module demonstrates a simple JPA setup with PostgreSQL, using Spring Data JPA and Docker for both testing and local development.

## Quick Start

### Running Tests
```bash
./gradlew database:postgres:test
```
This command will:
1. Start a PostgreSQL container using docker-compose
2. Run the tests
3. Automatically clean up the container afterward

### Local Development
To run the application locally with a PostgreSQL database:

```bash
# Start PostgreSQL container for development
docker compose up

# To stop and remove containers
docker compose down
```

## Configuration

### Docker Environments
- **Testing**: Uses `docker-compose.test.yml`
- **Development**: Uses `docker-compose.yml`

### Database Configuration
- Connection pooling is handled by HikariCP
- Default schema includes tables:
  - mythical_creature

### Data Persistence
By default, the database is recreated on each application start. To persist data between restarts:

1. Locate your `application.properties` or `application.yml`
2. Set the following property:
```yaml
spring:
  jpa:
    hibernate:
      ddl-auto: update
```

## Module Dependencies
This module relies on core components found in:
- `core/springJpaCore`: Base JPA configurations and utilities
- `core/sharedSql`: Common SQL-related functionality

## Creating Custom Services
You can extend base classes from `springJpaCore` to create your own service groups. This provides:
- Standard CRUD operations
- Common entity management patterns
- Consistent transaction handling

## Running Tests Locally
You have two options for running tests:

1. **Gradle Command** (Recommended):
```bash
./gradlew database:postgres:test
```

2. **Manual Docker Setup**:
```bash
# Start test database
docker compose -f docker-compose.test.yml up

# Run tests
# (Keep the container running for multiple test runs)

# Clean up when done
docker compose -f docker-compose.test.yml down
```

## Notes
- The test configuration automatically handles container lifecycle
- Uses HikariCP for efficient connection pooling
- Integration tests use a separate Docker configuration to isolate test data