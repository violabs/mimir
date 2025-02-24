# PostgreSQL Integration Guide 🐘

This guide covers the PostgreSQL integration example in the Mimir project, demonstrating best practices for using PostgreSQL with Spring Boot.

## Table of Contents
- [Quick Start](#quick-start)
- [Features](#features)
- [Configuration](#configuration)
- [Docker Integration](#docker-integration)
- [Development Setup](#development-setup)
- [Testing](#testing)
- [Best Practices](#best-practices)
- [Troubleshooting](#troubleshooting)

## Quick Start 🚀

### Prerequisites
- Docker Desktop
- Java 21+
- Kotlin 2.x

### Basic Setup
1. Navigate to the postgres module:
```bash
cd database/postgres
```

2. Start the development database:
```bash
# Using dev profile
docker compose --profile dev up
```

3. Run the application:
```bash
../../gradlew bootRun
```

## Features ✨

- Spring Data JPA integration
- HikariCP connection pooling
- Flyway migrations support
- Docker-based development and testing environments
- Transaction management
- Entity auditing
- Custom repository implementations

## Configuration 🔧

### Application Properties
```yaml
spring:
  datasource:
    username: postgres
    password: password
    url: jdbc:postgresql://localhost:5432/postgres
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
```

### HikariCP Configuration
```yaml
spring:
  datasource:
    hikari:
      maximum-pool-size: 10
      minimum-idle: 5
      idle-timeout: 300000
      connection-timeout: 20000
      max-lifetime: 1200000
```

### Entity Configuration
```kotlin
@Entity
@Table(name = "mythical_creature")
data class MythicalCreature(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    
    @Column(nullable = false)
    val name: String,
    
    @Column
    val description: String? = null
)
```

## Docker Integration 🐳

### Development Environment
```yaml
# docker/docker-compose.yml
services:
  dev-postgres:
    image: postgres:latest
    profiles: [dev]
    ports:
      - "5432:5432"
    environment:
      POSTGRES_USER: postgres
      POSTGRES_PASSWORD: password
      POSTGRES_DB: postgres
```

### Test Environment
```yaml
# docker/docker-compose.yml
services:
  test-postgres:
    image: postgres:latest
    profiles: [test]
    ports:
      - "5433:5432"
    environment:
      POSTGRES_USER: postgres
      POSTGRES_PASSWORD: password
      POSTGRES_DB: postgres
```

## Development Setup 💻

### Repository Pattern
```kotlin
@Repository
interface MythicalCreatureRepository : JpaRepository<MythicalCreature, Long> {
    fun findByNameContainingIgnoreCase(name: String): List<MythicalCreature>
}
```

### Service Layer
```kotlin
@Service
@Transactional
class MythicalCreatureService(
    private val repository: MythicalCreatureRepository
) {
    fun create(creature: MythicalCreature): MythicalCreature =
        repository.save(creature)

    fun findByName(name: String): List<MythicalCreature> =
        repository.findByNameContainingIgnoreCase(name)
}
```

## Testing 🧪

### Unit Tests
```kotlin
@Test
fun `should create mythical creature`() {
    // Given
    val creature = MythicalCreature(name = "Dragon")

    // When
    val saved = service.create(creature)

    // Then
    assertThat(saved.id).isNotNull()
    assertThat(saved.name).isEqualTo("Dragon")
}
```

### Integration Tests
```kotlin
@SpringBootTest
@TestPropertySource(locations = ["classpath:application-test.yml"])
class MythicalCreatureIntegrationTest {
    @Autowired
    lateinit var repository: MythicalCreatureRepository

    @Test
    fun `should persist creature to database`() {
        // Test implementation
    }
}
```

## Best Practices 🎯

1. **Connection Pooling**
   - Use HikariCP for connection management
   - Configure pool size based on workload
   - Monitor connection metrics

2. **Transaction Management**
   - Use @Transactional appropriately
   - Consider isolation levels
   - Handle transaction boundaries

3. **Entity Design**
   - Use appropriate column types
   - Consider indexing strategies
   - Implement auditing where needed

4. **Testing Strategy**
   - Separate test database
   - Clean state for each test
   - Use appropriate test data

## Troubleshooting 🔍

### Common Issues

1. **Connection Refused**
   - Check Docker container status
   - Verify port mappings
   - Confirm network configuration

2. **Schema Issues**
   - Check ddl-auto setting
   - Verify migration scripts
   - Check table ownership

3. **Performance Issues**
   - Monitor connection pool
   - Check query plans
   - Review indexing strategy

### Monitoring

1. **Connection Pool**
```sql
SELECT * FROM pg_stat_activity;
```

2. **Table Statistics**
```sql
SELECT * FROM pg_stat_user_tables;
```

## Additional Resources 📚

- [Spring Data JPA Documentation](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/)
- [PostgreSQL Documentation](https://www.postgresql.org/docs/)
- [HikariCP Documentation](https://github.com/brettwooldridge/HikariCP)
- [Docker PostgreSQL Image](https://hub.docker.com/_/postgres)