package io.violabs.mimir.schemaregistry.jsonschema.config

import io.confluent.kafka.schemaregistry.client.CachedSchemaRegistryClient
import io.confluent.kafka.schemaregistry.client.SchemaRegistryClient
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SchemaRegistryConfig {
    private val logger = LoggerFactory.getLogger(SchemaRegistryConfig::class.java)

    @Bean
    fun schemaRegistryClient(kafkaConfigProps: KafkaConfigProps): SchemaRegistryClient {
        logger.info("Schema Registry client initialized for URL: ${kafkaConfigProps.schemaRegistryUrl}")
        return CachedSchemaRegistryClient(kafkaConfigProps.schemaRegistryUrl, 100)
    }
}