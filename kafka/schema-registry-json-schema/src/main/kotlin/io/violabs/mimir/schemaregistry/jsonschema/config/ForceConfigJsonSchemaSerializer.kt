package io.violabs.mimir.schemaregistry.jsonschema.config

import io.confluent.kafka.schemaregistry.client.SchemaRegistryClient
import io.confluent.kafka.serializers.json.KafkaJsonSchemaSerializer


class ForceConfigJsonSchemaSerializer<T>(
    schemaRegistryClient: SchemaRegistryClient
) : KafkaJsonSchemaSerializer<T>(schemaRegistryClient) {

    override fun configure(configs: MutableMap<String, *>, isKey: Boolean) {
        val forcedConfigs = configs.toMutableMap().apply {
            put("json.schema.spec.version", "draft_2019_09")
            put("auto.register.schemas", true)
            put("use.latest.version", true)
            put("latest.compatibility.strict", false)
        }

        println("=== BEFORE SUPER.CONFIGURE ===")
        println("Original configs: $configs")
        println("Forced configs: $forcedConfigs")

        super.configure(forcedConfigs, isKey)

        println("=== AFTER SUPER.CONFIGURE ===")
        // Try to access the internal config to verify
        try {
            val field = this.javaClass.superclass.getDeclaredField("schemaRegistry")
            field.isAccessible = true
            val registry = field.get(this)
            println("Schema registry instance: $registry")
        } catch (e: Exception) {
            println("Could not access internal config: ${e.message}")
        }
    }
}