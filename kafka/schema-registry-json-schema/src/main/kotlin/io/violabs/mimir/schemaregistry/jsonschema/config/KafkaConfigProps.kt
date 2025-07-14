package io.violabs.mimir.schemaregistry.jsonschema.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.kafka.listener.ContainerProperties

@ConfigurationProperties(prefix = "spring.kafka")
class KafkaConfigProps(
    val bootstrapServers: String,
    val schemaRegistryUrl: String,
    val producer: Producer,
    val consumer: Consumer,
    val listener: Listener
) {

    @ConfigurationProperties(prefix = "spring.kafka.producer")
    class Producer(private val properties: Map<String, String>) {
        fun property(key: String, defaultValue: Any): String {
            return properties[key] ?: defaultValue.toString()
        }
    }

    @ConfigurationProperties(prefix = "spring.kafka.consumer")
    class Consumer(
        val groupId: String,
        val autoOffsetReset: String,
        val properties: Map<String, String>
    ) {
        fun property(key: String, defaultValue: Any): String {
            return properties[key] ?: defaultValue.toString()
        }
    }

    @ConfigurationProperties(prefix = "spring.kafka.listener")
    class Listener(private val ackMode: String) {
        val acknowledgmentMode: ContainerProperties.AckMode
            get() = ContainerProperties.AckMode.valueOf(ackMode.uppercase())
    }
}