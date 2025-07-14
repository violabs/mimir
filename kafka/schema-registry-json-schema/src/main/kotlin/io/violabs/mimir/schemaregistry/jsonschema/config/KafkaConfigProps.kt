package io.violabs.mimir.schemaregistry.jsonschema.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "spring.kafka")
class KafkaConfigProps(val schemaRegistryUrl: String)