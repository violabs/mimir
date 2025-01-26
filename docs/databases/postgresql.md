# PostgreSQL Integration 🐘

## Setup
```yaml
services:
  postgres:
    image: postgres:latest
    environment:
      POSTGRES_USER: mimir
      POSTGRES_PASSWORD: mimir
      POSTGRES_DB: mimir
    ports:
      - "5432:5432"
```

## Spring Configuration 🔧
```kotlin
@Configuration
class PostgresConfig {
    @Bean
    fun dataSource(): DataSource {
        return HikariDataSource().apply {
            jdbcUrl = "jdbc:postgresql://localhost:5432/mimir"
            username = "mimir"
            password = "mimir"
        }
    }
}
```

## Features 🌟
- R2DBC Support
- JSON/JSONB Operations
- Full-Text Search
- Partitioning Examples
- Async Operations

## Testing 🧪
```groovy
def "should save and retrieve data"() {
    given:
    def entity = new TestEntity(name: "test")
    
    when:
    repository.save(entity)
    
    then:
    repository.findById(entity.id).isPresent()
}
```