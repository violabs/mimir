# MySQL Integration

This module demonstrates MySQL integration with Spring Boot JPA using Kotlin. It provides both development and test configurations using Docker Compose.

## Prerequisites

- Docker and Docker Compose
- JDK 17 or later
- MySQL 8.0 or later (if running locally without Docker)

## Configuration

### Build Dependencies

The module uses the following key dependencies:

```kotlin
dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
    runtimeOnly("com.mysql:mysql-connector-j:9.0.0")
}
```

### Spring Configuration

The application supports different profiles for development and testing. Key configuration properties:

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/mimir
    username: mimir_user
    password: mimir_pass
  jpa:
    hibernate:
      ddl-auto: create-drop # Use 'update' for persistent data
    properties:
      hibernate:
        dialect: org.hibernate.dialect.MySQLDialect
```

## Docker Setup

### Development Environment

1. Start the development database:
   ```bash
   docker compose up
   ```

2. Stop and remove containers (keeping data):
   ```bash
   docker compose down
   ```

3. Remove all data:
   ```bash
   docker compose down -v
   ```

### Test Environment

1. Start the test database:
   ```bash
   docker compose -f docker/docker-compose.yml --profile test up
   ```

2. Clean up test environment:
   ```bash
   docker compose -f docker/docker-compose.yml --profile test down -v
   ```

## Integration with Core Module

This module extends the base JPA functionality from `springJpaCore`. Key integration points:

1. Entity Classes: Extend base entities from core module
2. Repositories: Extend base repositories for common CRUD operations
3. Services: Implement service interfaces defined in core

## Usage Example

```kotlin
@Entity
data class MyEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    
    val name: String
) : BaseEntity()

@Repository
interface MyRepository : BaseRepository<MyEntity, Long>

@Service
class MyService(
    repository: MyRepository
) : BaseService<MyEntity, Long>(repository)
```

## Testing

### Unit Tests

Run unit tests with:
```bash
./gradlew :database:mysql:test
```

### Integration Tests

Integration tests automatically manage the Docker container lifecycle:

1. Tests use a dedicated Docker container
2. Each test class gets a clean database state
3. Container is automatically started/stopped via Gradle's dockerCompose plugin

## Connection Pooling

The module uses HikariCP for connection pooling. Key configuration properties:

```yaml
spring:
  datasource:
    hikari:
      maximum-pool-size: 10
      minimum-idle: 5
      idle-timeout: 300000
```

## Performance Optimization

1. Batch Operations:
   ```yaml
   spring:
     jpa:
       properties:
         hibernate:
           jdbc:
             batch_size: 50
   ```

2. Statement Caching:
   ```yaml
   spring:
     datasource:
       hikari:
         data-source-properties:
           cachePrepStmts: true
           prepStmtCacheSize: 250
           prepStmtCacheSqlLimit: 2048
   ```

## Troubleshooting

1. Connection Issues:
   - Verify Docker container is running: `docker ps`
   - Check logs: `docker compose logs mysql`
   - Verify port mapping: `docker compose ps`

2. Performance Issues:
   - Monitor connection pool: Enable Hikari metrics
   - Check slow query log
   - Verify indexes on frequently queried columns

## Migration from PostgreSQL

If migrating from PostgreSQL, note these key differences:

1. Different SQL dialect
2. Case sensitivity in table/column names
3. Different text types (TEXT vs LONGTEXT)
4. Sequence vs AUTO_INCREMENT

## Security Considerations

1. Use connection encryption:
   ```yaml
   spring:
     datasource:
       url: jdbc:mysql://localhost:3306/mimir?useSSL=true
   ```

2. Configure user permissions appropriately
3. Use environment variables for sensitive configuration
4. Regularly update dependencies for security patches

## Contributing

1. Fork the repository
2. Create a feature branch
3. Submit a pull request

## License

This module is part of the Mimir project and is licensed under the same terms as the main project.