package io.violabs.mimir.schemaregistry.jsonschema.config

import io.confluent.kafka.schemaregistry.client.SchemaRegistryClient
import io.confluent.kafka.schemaregistry.json.SpecificationVersion
import io.confluent.kafka.schemaregistry.utils.EnumRecommender
import io.confluent.kafka.serializers.AbstractKafkaSchemaSerDeConfig
import io.confluent.kafka.serializers.json.KafkaJsonSchemaDeserializer
import io.confluent.kafka.serializers.json.KafkaJsonSchemaDeserializerConfig
import io.confluent.kafka.serializers.json.KafkaJsonSchemaSerializer
import io.confluent.kafka.serializers.json.KafkaJsonSchemaSerializerConfig
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
class KafkaConfig(
    private val kafkaConfigProps: KafkaConfigProps,
    private val schemaRegistryClient: SchemaRegistryClient
) {

    private fun producerFactory(): ProducerFactory<String, UserEventV1> {
        val producerProps = kafkaConfigProps.producer
        val configProps = mapOf<String, Any>(
            ProducerConfig.BOOTSTRAP_SERVERS_CONFIG to kafkaConfigProps.bootstrapServers,
            AbstractKafkaSchemaSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG to kafkaConfigProps.schemaRegistryUrl,
            AbstractKafkaSchemaSerDeConfig.AUTO_REGISTER_SCHEMAS to producerProps.property("auto.register.schemas", false),
            KafkaJsonSchemaSerializerConfig.FAIL_INVALID_SCHEMA to producerProps.property("json.fail.invalid.schema", true),
            KafkaJsonSchemaSerializerConfig.WRITE_DATES_AS_ISO8601 to producerProps.property("json.write.dates.iso8601", true),
            // This will be overridden by our custom serializer, but good to have as fallback
//            KafkaJsonSchemaSerializerConfig.SCHEMA_SPEC_VERSION to "draft_2020_12"
            KafkaJsonSchemaSerializerConfig.SCHEMA_SPEC_VERSION to "draft_2019_09"
        )
        println("Producer config: $configProps")
        val serializer = ForceConfigJsonSchemaSerializer<UserEventV1>(schemaRegistryClient)
        return DefaultKafkaProducerFactory(configProps, StringSerializer(), serializer)
    }

    // Type-specific templates for better type safety
    @Bean
    fun userEventKafkaTemplate(): KafkaTemplate<String, UserEventV1> {
        return KafkaTemplate(producerFactory())
    }

    // Consumer factory for UserEvent
    @Bean
    fun userEventConsumerFactory(): ConsumerFactory<String, UserEventV1> {
        val configProps = mapOf<String, Any>(
            ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG to kafkaConfigProps.bootstrapServers,
            ConsumerConfig.GROUP_ID_CONFIG to kafkaConfigProps.consumer.groupId,
            AbstractKafkaSchemaSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG to kafkaConfigProps.schemaRegistryUrl,
            KafkaJsonSchemaDeserializerConfig.JSON_VALUE_TYPE to UserEvent::class.java.name,
            KafkaJsonSchemaDeserializerConfig.FAIL_INVALID_SCHEMA to kafkaConfigProps.consumer.property("json.fail.invalid.schema", true),
            ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG to kafkaConfigProps.consumer.property("enable.auto.commit", false),
            KafkaJsonSchemaSerializerConfig.SCHEMA_SPEC_VERSION to "draft_2020_12"
        )
//        val deserializer = KafkaJsonSchemaDeserializer<UserEventV1>(schemaRegistryClient, configProps)
        val deserializer = KafkaJsonSchemaDeserializer<UserEventV1>(schemaRegistryClient, configProps)
        return DefaultKafkaConsumerFactory(configProps, StringDeserializer(), deserializer)
    }

    // Listener container factory for UserEvent
    @Bean
    fun kafkaListenerContainerFactory(): ConcurrentKafkaListenerContainerFactory<String, UserEventV1> {
        val factory = ConcurrentKafkaListenerContainerFactory<String, UserEventV1>()
        factory.consumerFactory = userEventConsumerFactory()
        factory.containerProperties.ackMode = ContainerProperties.AckMode.MANUAL_IMMEDIATE
        return factory
    }
}