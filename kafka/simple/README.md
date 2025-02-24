# Kafka Integration

This module demonstrates Spring Kafka integration with Kotlin, providing both development and test configurations using Docker Compose.

## Prerequisites

- Docker and Docker Compose
- JDK 17 or later
- Apache Kafka 3.x (if running locally without Docker)

## Quick Start

1. Start Kafka and the UI:
   ```bash
   docker compose up -d
   ```

2. Access Kafka UI:
   - URL: http://localhost:8080
   - Configure topics and monitor message flow

3. Stop services:
   ```bash
   docker compose down
   ```

## Configuration

### Build Dependencies

```kotlin
dependencies {
    implementation("org.springframework.kafka:spring-kafka")
    testImplementation("org.springframework.kafka:spring-kafka-test")
}
```

### Spring Configuration

```yaml
spring:
  kafka:
    bootstrap-servers: localhost:29092
    producer:
      key-serializer: org.apache.kafka.common.serialization.StringSerializer
      value-serializer: org.springframework.kafka.support.serializer.JsonSerializer
    consumer:
      group-id: mimir-group
      auto-offset-reset: earliest
      key-deserializer: org.apache.kafka.common.serialization.StringDeserializer
      value-deserializer: org.springframework.kafka.support.serializer.JsonDeserializer
      properties:
        spring.json.trusted.packages: "*"
```

## Usage Examples

### Producer Configuration

```kotlin
@Configuration
class KafkaProducerConfig {
    @Bean
    fun producerFactory(
        @Value("\${spring.kafka.bootstrap-servers}") bootstrapServers: String
    ): ProducerFactory<String, Any> = DefaultKafkaProducerFactory(mapOf(
        ProducerConfig.BOOTSTRAP_SERVERS_CONFIG to bootstrapServers,
        ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG to StringSerializer::class.java,
        ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG to JsonSerializer::class.java
    ))

    @Bean
    fun kafkaTemplate(producerFactory: ProducerFactory<String, Any>) = 
        KafkaTemplate(producerFactory)
}
```

### Consumer Configuration

```kotlin
@Configuration
class KafkaConsumerConfig {
    @Bean
    fun consumerFactory(
        @Value("\${spring.kafka.bootstrap-servers}") bootstrapServers: String,
        @Value("\${spring.kafka.consumer.group-id}") groupId: String
    ): ConsumerFactory<String, Any> = DefaultKafkaConsumerFactory(mapOf(
        ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG to bootstrapServers,
        ConsumerConfig.GROUP_ID_CONFIG to groupId,
        ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG to StringDeserializer::class.java,
        ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG to JsonDeserializer::class.java,
        ConsumerConfig.AUTO_OFFSET_RESET_CONFIG to "earliest"
    ))

    @Bean
    fun kafkaListenerContainerFactory(
        consumerFactory: ConsumerFactory<String, Any>
    ): ConcurrentKafkaListenerContainerFactory<String, Any> =
        ConcurrentKafkaListenerContainerFactory<String, Any>().apply {
            this.consumerFactory = consumerFactory
        }
}
```

### Message Producer

```kotlin
@Service
class MessageProducer(
    private val kafkaTemplate: KafkaTemplate<String, Any>
) {
    fun sendMessage(topic: String, key: String, message: Any) {
        kafkaTemplate.send(topic, key, message)
            .addCallback(
                { result -> logger.info { "Message sent: ${result?.recordMetadata}" } },
                { ex -> logger.error(ex) { "Failed to send message" } }
            )
    }
}
```

### Message Consumer

```kotlin
@Service
class MessageConsumer {
    @KafkaListener(topics = ["my-topic"], groupId = "\${spring.kafka.consumer.group-id}")
    fun listen(message: String) {
        logger.info { "Received message: $message" }
    }
}
```

## Testing

### Unit Testing with Embedded Kafka

```kotlin
@SpringBootTest
@EmbeddedKafka(
    partitions = 1,
    brokerProperties = ["listeners=PLAINTEXT://localhost:9092", "port=9092"]
)
class MessageServiceTest {
    @Autowired
    private lateinit var producer: MessageProducer

    @Autowired
    private lateinit var consumer: MessageConsumer

    @Test
    fun `should send and receive message`() {
        // Test implementation
    }
}
```

### Integration Testing

```kotlin
@SpringBootTest
@TestPropertySource(properties = [
    "spring.kafka.bootstrap-servers=\${spring.embedded.kafka.brokers}"
])
class KafkaIntegrationTest {
    // Test implementation
}
```

## Monitoring and Management

### Kafka UI

The included Kafka UI provides:
- Topic management
- Consumer group monitoring
- Message browsing
- Schema registry integration

Access at: http://localhost:8080

### Metrics and Monitoring

1. Enable Kafka metrics in application.yml:
   ```yaml
   management:
     metrics:
       export:
         prometheus:
           enabled: true
     endpoints:
       web:
         exposure:
           include: prometheus,health,info
   ```

2. Monitor using:
   - Prometheus
   - Grafana
   - Spring Boot Actuator endpoints

## Troubleshooting

### Common Issues

1. Connection Refused
   - Verify Docker containers are running
   - Check port mappings
   - Validate bootstrap server configuration

2. Message Not Received
   - Check consumer group ID
   - Verify topic exists
   - Check serialization configuration

3. Serialization Errors
   - Verify message format matches serializer
   - Check trusted packages configuration
   - Validate JSON structure

### Debugging

Enable debug logging:
```yaml
logging:
  level:
    org.apache.kafka: DEBUG
    org.springframework.kafka: DEBUG
```

## Performance Optimization

1. Producer Configuration:
   ```kotlin
   ProducerConfig.BATCH_SIZE_CONFIG to "16384"
   ProducerConfig.LINGER_MS_CONFIG to "1"
   ProducerConfig.COMPRESSION_TYPE_CONFIG to "snappy"
   ```

2. Consumer Configuration:
   ```kotlin
   ConsumerConfig.FETCH_MIN_BYTES_CONFIG to "1024"
   ConsumerConfig.FETCH_MAX_WAIT_MS_CONFIG to "500"
   ```

## Security

1. SSL Configuration:
   ```yaml
   spring:
     kafka:
       security:
         protocol: SSL
       ssl:
         trust-store-location: classpath:kafka.truststore.jks
         trust-store-password: password
         key-store-location: classpath:kafka.keystore.jks
         key-store-password: password
   ```

2. SASL Configuration:
   ```yaml
   spring:
     kafka:
       security:
         protocol: SASL_SSL
       properties:
         sasl.mechanism: PLAIN
         sasl.jaas.config: org.apache.kafka.common.security.plain.PlainLoginModule required username="user" password="password";
   ```

## References

- [Spring Kafka Documentation](https://docs.spring.io/spring-kafka/reference/html/)
- [Apache Kafka Documentation](https://kafka.apache.org/documentation/)
- [Kafka UI Documentation](https://github.com/provectus/kafka-ui)
- [Testing with Spring Kafka](https://www.baeldung.com/spring-boot-kafka-testing)

## Contributing

1. Fork the repository
2. Create a feature branch
3. Submit a pull request

## License

This module is part of the Mimir project and is licensed under the same terms as the main project.