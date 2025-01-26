# MySQL Integration 🐬

## Docker Setup
```yaml
services:
  mysql:
    image: mysql:latest
    environment:
      MYSQL_ROOT_PASSWORD: root
      MYSQL_DATABASE: mimir
      MYSQL_USER: mimir
      MYSQL_PASSWORD: mimir
    ports:
      - "3306:3306"
```

## Spring Configuration 🔧
```kotlin
@Configuration
class MySQLConfig {
    @Bean
    fun dataSource() = HikariDataSource().apply {
        jdbcUrl = "jdbc:mysql://localhost:3306/mimir"
        username = "mimir"
        password = "mimir"
        maximumPoolSize = 10
    }
}
```

## Repository Example 📦
```kotlin
@Repository
interface UserRepository : JpaRepository<User, Long> {
    fun findByEmail(email: String): User?
    
    @Query("SELECT u FROM User u WHERE u.lastLogin > :date")
    fun findRecentUsers(@Param("date") date: LocalDateTime): List<User>
}
```

## Features 🌟
- JPA/Hibernate Integration
- Connection Pooling
- Transaction Management
- Batch Operations
- Stored Procedures

## Testing 🧪
```groovy
def "should handle transactions"() {
    given:
    def user = new User(email: "test@example.com")
    
    when:
    transactionTemplate.execute { status ->
        repository.save(user)
        throw new RuntimeException("Rollback test")
    }
    
    then:
    thrown(RuntimeException)
    repository.findByEmail("test@example.com") == null
}
```