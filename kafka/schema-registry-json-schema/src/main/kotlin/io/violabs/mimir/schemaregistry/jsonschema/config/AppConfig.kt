package io.violabs.mimir.schemaregistry.jsonschema.config

import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@EnableConfigurationProperties(
    KafkaConfigProps::class,
    KafkaConfigProps.Producer::class,
    KafkaConfigProps.Producer.Properties::class,
    KafkaConfigProps.Consumer::class,
    KafkaConfigProps.Consumer.Properties::class,
    KafkaConfigProps.Listener::class
)
class AppConfig