package io.violabs.mimir.schemaregistry.jsonschema

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration
import org.springframework.boot.runApplication
import org.springframework.kafka.annotation.EnableKafka

@SpringBootApplication(exclude = [
    KafkaAutoConfiguration::class
])
@EnableKafka
class KafkaJsonSchemaApplication

fun main(args: Array<String>) {
    runApplication<KafkaJsonSchemaApplication>(*args)
}
