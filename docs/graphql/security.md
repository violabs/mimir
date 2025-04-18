# GraphQL Security Best Practices

This guide covers security considerations and implementations for GraphQL in the Mimir project.

## Authentication & Authorization

### Configuration

```kotlin
@Configuration
class GraphQLSecurityConfig {
    @Bean
    fun graphQLContextFactory(): GraphQLContextFactory {
        return SecurityAwareGraphQLContextFactory()
    }
}
```

### Directive Implementation

```kotlin
@Directive("@auth")
class AuthDirective : SchemaDirectiveWiring {
    override fun onField(field: GraphQLFieldDefinition): GraphQLFieldDefinition {
        val originalFetcher = field.dataFetcher
        
        return field.transform { builder ->
            builder.dataFetcher { env ->
                val context = env.getContext<SecurityContext>()
                if (!context.isAuthenticated()) {
                    throw UnauthorizedException("Authentication required")
                }
                originalFetcher.get(env)
            }
        }
    }
}
```

### Usage in Schema

```graphql
type Query {
    publicData: String
    privateData: String @auth
    adminData: String @auth(role: ADMIN)
}
```

## Input Validation

### Implementation

```kotlin
@Component
class InputValidator {
    fun validateUserInput(input: UserInput) {
        require(input.email.matches(EMAIL_REGEX)) { "Invalid email format" }
        require(input.username.length in 3..50) { "Username must be between 3 and 50 characters" }
    }
    
    companion object {
        private val EMAIL_REGEX = "[^@]+@[^@]+\\.[^@]+".toRegex()
    }
}
```

## Rate Limiting

### Configuration

```kotlin
@Configuration
class RateLimitConfig {
    @Bean
    fun rateLimiter(): GraphQLRateLimiter {
        return GraphQLRateLimiter(
            maxRequests = 100,
            perSeconds = 60
        )
    }
}
```

### Implementation

```kotlin
@Component
class QueryRateLimiter(
    private val rateLimiter: GraphQLRateLimiter
) {
    suspend fun checkRateLimit(context: GraphQLContext) {
        val clientId = context.getClientId()
        if (!rateLimiter.tryAcquire(clientId)) {
            throw TooManyRequestsException("Rate limit exceeded")
        }
    }
}
```

## Query Depth Limiting

### Configuration

```kotlin
@Configuration
class QueryDepthConfig {
    @Bean
    fun maxQueryDepthInstrumentation(): MaxQueryDepthInstrumentation {
        return MaxQueryDepthInstrumentation(maxDepth = 10)
    }
}
```

## Field Suggestion Prevention

### Configuration

```kotlin
@Configuration
class SuggestionConfig {
    @Bean
    fun fieldSuggestionInstrumentation(): FieldSuggestionInstrumentation {
        return FieldSuggestionInstrumentation(enabled = false)
    }
}
```

## Error Handling

### Custom Error Types

```kotlin
sealed class GraphQLSecurityError : GraphQLError {
    data class AuthenticationError(override val message: String) : GraphQLSecurityError()
    data class AuthorizationError(override val message: String) : GraphQLSecurityError()
    data class ValidationError(override val message: String) : GraphQLSecurityError()
    data class RateLimitError(override val message: String) : GraphQLSecurityError()
}
```

### Error Handler

```kotlin
@Component
class SecurityErrorHandler {
    fun handleError(error: Throwable): GraphQLError = when (error) {
        is UnauthorizedException -> GraphQLSecurityError.AuthenticationError(error.message ?: "Authentication required")
        is ForbiddenException -> GraphQLSecurityError.AuthorizationError(error.message ?: "Access denied")
        is ValidationException -> GraphQLSecurityError.ValidationError(error.message ?: "Invalid input")
        is RateLimitException -> GraphQLSecurityError.RateLimitError(error.message ?: "Rate limit exceeded")
        else -> GenericGraphQLError(error.message ?: "Internal server error")
    }
}
```

## Security Headers

### Configuration

```kotlin
@Configuration
class SecurityHeadersConfig {
    @Bean
    fun securityHeadersFilter(): WebFilter {
        return WebFilter { exchange, chain ->
            exchange.response.headers.apply {
                add("X-Content-Type-Options", "nosniff")
                add("X-Frame-Options", "DENY")
                add("X-XSS-Protection", "1; mode=block")
                add("Referrer-Policy", "strict-origin-when-cross-origin")
            }
            chain.filter(exchange)
        }
    }
}
```

## CORS Configuration

```kotlin
@Configuration
class CorsConfig {
    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val configuration = CorsConfiguration().apply {
            allowedOrigins = listOf("https://trusted-domain.com")
            allowedMethods = listOf("GET", "POST")
            allowedHeaders = listOf("Content-Type", "Authorization")
            exposedHeaders = listOf("X-Custom-Header")
            allowCredentials = true
            maxAge = 3600L
        }
        
        return UrlBasedCorsConfigurationSource().apply {
            registerCorsConfiguration("/graphql", configuration)
        }
    }
}
```

## Best Practices

1. **Input Sanitization**
   - Validate all input fields
   - Sanitize string inputs
   - Check array lengths
   - Validate enums

2. **Authentication**
   - Use secure session management
   - Implement token-based auth
   - Handle token expiration
   - Secure token storage

3. **Authorization**
   - Implement role-based access
   - Use field-level permissions
   - Audit access patterns
   - Regular permission reviews

4. **Rate Limiting**
   - Per-user limits
   - Per-IP limits
   - Complex query limits
   - Timeout policies

5. **Monitoring**
   - Log security events
   - Monitor auth failures
   - Track rate limit hits
   - Alert on anomalies