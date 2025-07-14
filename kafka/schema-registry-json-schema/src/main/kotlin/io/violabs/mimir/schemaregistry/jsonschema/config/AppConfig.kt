package io.violabs.mimir.schemaregistry.jsonschema.config

import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@EnableConfigurationProperties(
    KafkaConfigProps::class,
)
class AppConfig