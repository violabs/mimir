package io.violabs.mimir.kafka.schemaregistry.avro

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class KafkaAvroApplication

fun main(args: Array<String>) {
    runApplication<KafkaAvroApplication>(*args)
}