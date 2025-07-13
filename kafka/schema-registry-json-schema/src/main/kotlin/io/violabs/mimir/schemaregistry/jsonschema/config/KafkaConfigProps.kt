package io.violabs.mimir.schemaregistry.jsonschema.config

import io.confluent.kafka.serializers.AbstractKafkaSchemaSerDeConfig
import io.confluent.kafka.serializers.json.KafkaJsonSchemaDeserializerConfig
import io.confluent.kafka.serializers.json.KafkaJsonSchemaSerializerConfig
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.producer.ProducerConfig
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
    fun toProducerMap(): Map<String, Any> {
        return mapOf(
            ProducerConfig.BOOTSTRAP_SERVERS_CONFIG to bootstrapServers,
            AbstractKafkaSchemaSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG to schemaRegistryUrl,
            ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG to producer.keySerializer,
            ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG to producer.valueSerializer,
            AbstractKafkaSchemaSerDeConfig.AUTO_REGISTER_SCHEMAS to (producer.properties["autoRegisterSchemas"] ?: true),
            AbstractKafkaSchemaSerDeConfig.USE_LATEST_VERSION to (producer.properties["useLatestVersion"] ?: true),
            KafkaJsonSchemaSerializerConfig.FAIL_INVALID_SCHEMA to (producer.properties["jsonFailInvalidSchema"] ?: true),
            KafkaJsonSchemaSerializerConfig.WRITE_DATES_AS_ISO8601 to (producer.properties["jsonWriteDatesIso8601"] ?: true),
            KafkaJsonSchemaSerializerConfig.LATEST_COMPATIBILITY_STRICT to (producer.properties["latestCompatibilityStrict"] ?: true),
            KafkaJsonSchemaSerializerConfig.SCHEMA_SPEC_VERSION to (producer.properties["jsonSchemaSpecVersion"] ?: true),
        )
            .also { println("Producer Config: $it") }
    }

    fun toConsumerMap(): Map<String, Any> {
        return mapOf(
            ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG to bootstrapServers,
            AbstractKafkaSchemaSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG to schemaRegistryUrl,
            ConsumerConfig.GROUP_ID_CONFIG to consumer.groupId,
            ConsumerConfig.AUTO_OFFSET_RESET_CONFIG to consumer.autoOffsetReset,
            KafkaJsonSchemaDeserializerConfig.FAIL_INVALID_SCHEMA to (consumer.properties["jsonFailInvalidSchema"] ?: true),
            "specific.json.reader" to (consumer.properties["specificJsonReader"] ?: false),
        )
    }

    @ConfigurationProperties(prefix = "spring.kafka.producer")
    class Producer(
        val keySerializer: String,
        val valueSerializer: String,
        val properties: Map<String, String>
    ) {
//        @ConfigurationProperties(prefix = "spring.kafka.producer.properties")
//        class Properties(
//            val autoRegisterSchemas: Boolean,
//            val useLatestVersion: Boolean,
//            val jsonFailInvalidSchema: Boolean,
//            val jsonWriteDatesIso8601: Boolean,
//            val latestCompatibilityStrict: Boolean,
//            val jsonSchemaSpecVersion: String
//        )
    }

    @ConfigurationProperties(prefix = "spring.kafka.consumer")
    class Consumer(
        val keyDeserializer: String,
        val valueDeserializer: String,
        val groupId: String,
        val autoOffsetReset: String,
        val properties: Map<String, String>
    ) {
//        @ConfigurationProperties(prefix = "spring.kafka.consumer.properties")
//        class Properties(
//            val jsonFailInvalidSchema: Boolean,
//            val specificJsonReader: Boolean
//        )
    }

    @ConfigurationProperties(prefix = "spring.kafka.listener")
    class Listener(private val ackMode: String) {
        val acknowledgmentMode: ContainerProperties.AckMode
            get() = ContainerProperties.AckMode.valueOf(ackMode.uppercase())
    }
}