package io.violabs.mimir.schemaregistry.jsonschema.config

import io.confluent.kafka.serializers.AbstractKafkaSchemaSerDeConfig
import io.confluent.kafka.serializers.json.KafkaJsonSchemaDeserializerConfig
import io.confluent.kafka.serializers.json.KafkaJsonSchemaSerializerConfig
import io.violabs.mimir.schemaregistry.jsonschema.config.serde.CustomJsonSchemaDeserializer
import io.violabs.mimir.schemaregistry.jsonschema.config.serde.CustomJsonSchemaSerializer
import io.violabs.mimir.schemaregistry.jsonschema.config.serde.CustomJsonSchemaSerializer3
import io.violabs.mimir.schemaregistry.jsonschema.domain.UserEvent
import io.violabs.mimir.schemaregistry.jsonschema.domain.UserEventV1
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.*
import org.springframework.kafka.listener.ContainerProperties

@Configuration
class KafkaConfig {

    @Bean
    fun producerFactory(): ProducerFactory<String, Any> {
        val configProps = mapOf<String, Any>(
            ProducerConfig.BOOTSTRAP_SERVERS_CONFIG to "localhost:29092",
            ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG to StringSerializer::class.java,
            ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG to CustomJsonSchemaSerializer::class.java,
            AbstractKafkaSchemaSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG to "http://localhost:8090",
            AbstractKafkaSchemaSerDeConfig.AUTO_REGISTER_SCHEMAS to true,
            KafkaJsonSchemaSerializerConfig.FAIL_INVALID_SCHEMA to true,
            KafkaJsonSchemaSerializerConfig.WRITE_DATES_AS_ISO8601 to true,
            // This will be overridden by our custom serializer, but good to have as fallback
            KafkaJsonSchemaSerializerConfig.SCHEMA_SPEC_VERSION to "draft_2020_12"
        )
        return DefaultKafkaProducerFactory(configProps, StringSerializer(), CustomJsonSchemaSerializer3())
    }

    @Bean
    fun kafkaTemplate(): KafkaTemplate<String, Any> {
        return KafkaTemplate(producerFactory())
    }

    // Type-specific templates for better type safety
    @Bean
    fun userEventKafkaTemplate(): KafkaTemplate<String, UserEventV1> {
        val configProps = mapOf<String, Any>(
            ProducerConfig.BOOTSTRAP_SERVERS_CONFIG to "localhost:29092",
            ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG to StringSerializer::class.java,
            ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG to CustomJsonSchemaSerializer::class.java,
            AbstractKafkaSchemaSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG to "http://localhost:8090",
            AbstractKafkaSchemaSerDeConfig.AUTO_REGISTER_SCHEMAS to true,
            KafkaJsonSchemaSerializerConfig.FAIL_INVALID_SCHEMA to true,
            KafkaJsonSchemaSerializerConfig.WRITE_DATES_AS_ISO8601 to true
        )
        return KafkaTemplate(
            DefaultKafkaProducerFactory(
                configProps,
                StringSerializer(),
                CustomJsonSchemaSerializer3()
            )
        )
    }

    // Consumer factory for UserEvent
    @Bean
    fun userEventConsumerFactory(): ConsumerFactory<String, UserEvent> {
        val configProps = mapOf<String, Any>(
            ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG to "localhost:29092",
            ConsumerConfig.GROUP_ID_CONFIG to "user-event-consumer-group",
            ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG to StringDeserializer::class.java,
            ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG to CustomJsonSchemaDeserializer::class.java,
            AbstractKafkaSchemaSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG to "http://localhost:8090",
            KafkaJsonSchemaDeserializerConfig.JSON_VALUE_TYPE to UserEvent::class.java.name,
            KafkaJsonSchemaDeserializerConfig.FAIL_INVALID_SCHEMA to true,
            ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG to false
        )
        return DefaultKafkaConsumerFactory(configProps, StringDeserializer(), CustomJsonSchemaDeserializer())
    }

    // Listener container factory for UserEvent
    @Bean
    fun userEventKafkaListenerContainerFactory(): ConcurrentKafkaListenerContainerFactory<String, UserEvent> {
        val factory = ConcurrentKafkaListenerContainerFactory<String, UserEvent>()
        factory.consumerFactory = userEventConsumerFactory()
        factory.containerProperties.ackMode = ContainerProperties.AckMode.MANUAL_IMMEDIATE
        return factory
    }
}