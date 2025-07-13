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

class CustomJsonSchemaDeserializer<T> : io.confluent.kafka.serializers.json.KafkaJsonSchemaDeserializer<T>() {

    override fun configure(configs: MutableMap<String, *>?, isKey: Boolean) {
        println("🔧 CustomJsonSchemaDeserializer.configure() called")

        // Create mutable copy and force JSON Schema 2020-12
        val modifiedConfigs = configs?.toMutableMap() ?: mutableMapOf()
        modifiedConfigs[KafkaJsonSchemaSerializerConfig.SCHEMA_SPEC_VERSION] = SpecificationVersion.DRAFT_2020_12.toString()

        println("🔧 Forcing JSON Schema spec version to: ${SpecificationVersion.DRAFT_2020_12}")
        println("🔧 All configs: $modifiedConfigs")

        // Call parent configure with modified configs
        super.configure(modifiedConfigs, isKey)
    }

    override fun deserialize(topic: String?, data: ByteArray?): T? {
        println("📥 CustomJsonSchemaDeserializer.deserialize() called for topic: $topic")
        return super.deserialize(topic, data)
    }
}