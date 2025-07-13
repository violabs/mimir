package io.violabs.mimir.schemaregistry.jsonschema.config.serde

import io.confluent.kafka.schemaregistry.client.CachedSchemaRegistryClient
import io.confluent.kafka.schemaregistry.json.SpecificationVersion
import io.confluent.kafka.serializers.AbstractKafkaSchemaSerDeConfig
import io.confluent.kafka.serializers.json.AbstractKafkaJsonSchemaDeserializer
import io.confluent.kafka.serializers.json.AbstractKafkaJsonSchemaSerializer
import io.confluent.kafka.serializers.json.KafkaJsonSchemaDeserializer
import io.confluent.kafka.serializers.json.KafkaJsonSchemaDeserializerConfig
import io.confluent.kafka.serializers.json.KafkaJsonSchemaSerializer
import io.confluent.kafka.serializers.json.KafkaJsonSchemaSerializerConfig
import jakarta.annotation.PostConstruct
import org.apache.kafka.common.serialization.Deserializer
import org.apache.kafka.common.serialization.Serializer
import org.springframework.stereotype.Component

class CustomJsonSchemaDeserializer2<T> : AbstractKafkaJsonSchemaDeserializer<T?>(), Deserializer<T?> {
    private lateinit var internalSerializer: KafkaJsonSchemaDeserializer<T?>

    init {
        val configMap = mapOf<String, Any>(
            AbstractKafkaSchemaSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG to "http://localhost:8090",
            KafkaJsonSchemaDeserializerConfig.FAIL_INVALID_SCHEMA to true,
            "specific.json.reader" to true,
            KafkaJsonSchemaSerializerConfig.SCHEMA_SPEC_VERSION to SpecificationVersion.DRAFT_2020_12.toString()
        )
        val config = KafkaJsonSchemaDeserializerConfig(configMap)

        val schemaRegistryClient = CachedSchemaRegistryClient(
            "http://localhost:8090",
            100,
            config.originals()
        )
        internalSerializer = KafkaJsonSchemaDeserializer(schemaRegistryClient, configMap)
    }

    override fun close() {
        internalSerializer.close()
    }

    override fun deserialize(topic: String?, data: ByteArray?): T? {
        return internalSerializer.deserialize(topic, data)
    }
}