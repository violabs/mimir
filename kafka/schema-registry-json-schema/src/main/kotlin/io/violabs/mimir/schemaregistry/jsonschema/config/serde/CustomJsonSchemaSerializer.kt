package io.violabs.mimir.schemaregistry.jsonschema.config.serde

import io.confluent.kafka.schemaregistry.json.SpecificationVersion
import io.confluent.kafka.serializers.json.KafkaJsonSchemaSerializer
import io.confluent.kafka.serializers.json.KafkaJsonSchemaSerializerConfig

class CustomJsonSchemaSerializer<T> : KafkaJsonSchemaSerializer<T>() {

    override fun configure(configs: MutableMap<String, *>?, isKey: Boolean) {
        println("🔧 CustomJsonSchemaSerializer.configure() called")

        // Create mutable copy and force JSON Schema 2020-12
        val modifiedConfigs = configs?.toMutableMap() ?: mutableMapOf()
        modifiedConfigs[KafkaJsonSchemaSerializerConfig.SCHEMA_SPEC_VERSION] =
            SpecificationVersion.DRAFT_2020_12.toString()

        println("🔧 Forcing JSON Schema spec version to: ${SpecificationVersion.DRAFT_2020_12}")
        println("🔧 All configs: $modifiedConfigs")

        // Call parent configure with modified configs
        super.configure(modifiedConfigs, isKey)
    }

    override fun serialize(topic: String?, data: T?): ByteArray? {
        println("📤 CustomJsonSchemaSerializer.serialize() called for topic: $topic")
        return super.serialize(topic, data)
    }
}