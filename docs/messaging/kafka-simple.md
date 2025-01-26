# Simple Kafka Setup 🚀

## Docker Setup
```yaml
services:
  zookeeper:
    image: confluentinc/cp-zookeeper:latest
    environment:
      ZOOKEEPER_CLIENT_PORT: 2181
      
  kafka:
    image: confluentinc/cp-kafka:latest
    depends_on:
      - zookeeper
    environment:
      KAFKA_BROKER_ID: 1
      KAFKA_ZOOKEEPER_CONNECT: zookeeper:2181
      KAFKA_ADVERTISED_LISTENERS: PLAINTEXT://kafka:29092,PLAINTEXT_HOST://localhost:9092
```

## Producer Example 📤
```kotlin
@Service
class MessageProducer(
    private val kafkaTemplate: KafkaTemplate<String, String>
) {
    fun send(message: String) = 
        kafkaTemplate.send("topic", message)
}
```

## Consumer Example 📥
```kotlin
@Service
class MessageConsumer {
    @KafkaListener(topics = ["topic"])
    fun receive(message: String) {
        println("Received: $message")
    }
}
```

## Testing 🧪
```groovy
@SpringBootTest
class KafkaTest extends Specification {
    @Autowired
    MessageProducer producer
    
    def "should send and receive message"() {
        when:
        producer.send("test")
        
        then:
        // verify receipt
    }
}
```

## Features 🌟
- Basic Producer/Consumer
- Error Handling
- Dead Letter Queue
- Retry Policies
- Monitoring