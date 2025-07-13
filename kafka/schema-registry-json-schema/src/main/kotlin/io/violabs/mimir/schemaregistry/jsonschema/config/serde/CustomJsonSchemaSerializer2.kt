package io.violabs.mimir.schemaregistry.jsonschema.config.serde

import io.confluent.kafka.schemaregistry.client.SchemaRegistryClient
import io.confluent.kafka.schemaregistry.json.SpecificationVersion
import io.confluent.kafka.serializers.json.KafkaJsonSchemaSerializer
import io.confluent.kafka.serializers.json.KafkaJsonSchemaSerializerConfig
import jakarta.annotation.PostConstruct
import org.apache.kafka.common.header.Headers
import org.springframework.stereotype.Component

class CustomJsonSchemaSerializer2<T> : KafkaJsonSchemaSerializer<T?> {

    init {
        println("🔥 CustomJsonSchemaSerializer INIT BLOCK - Class is being loaded!")
    }

    constructor() : super() {
        println("🔧 CustomJsonSchemaSerializer() - No-arg constructor called")
        println("🔧 Class: ${this::class.java.name}")
        println("🔧 ClassLoader: ${this::class.java.classLoader}")
    }

    constructor(client: SchemaRegistryClient?) : super(client) {
        println("🔧 CustomJsonSchemaSerializer(client) - Constructor with client called")
    }

    constructor(client: SchemaRegistryClient?, props: MutableMap<String?, *>?) : super(client, props) {
        println("🔧 CustomJsonSchemaSerializer(client, props) - Constructor with client and props called")
    }

    constructor(client: SchemaRegistryClient?, props: MutableMap<String?, *>?, cacheCapacity: Int) : super(
        client,
        props,
        cacheCapacity
    ) {
        println("🔧 CustomJsonSchemaSerializer(client, props, capacity) - Full constructor called")
    }

    override fun configure(config: MutableMap<String?, *>, isKey: Boolean) {
        println("🚀 CUSTOM SERIALIZER CONFIGURE CALLED!")
        println("🔍 Original config: $config")
        println("🔑 Is key serializer: $isKey")

        // Create a mutable copy of the config and force JSON Schema 2020-12
        val enhancedConfig: MutableMap<String?, Any?> = HashMap(config)

        // Try multiple property approaches
        enhancedConfig["json.schema.spec.version"] = "draft_2020_12"
        enhancedConfig[KafkaJsonSchemaSerializerConfig.SCHEMA_SPEC_VERSION] = "draft_2020_12"

        println("📝 Enhanced config: $enhancedConfig")

        // Call parent configure with enhanced config
        super.configure(enhancedConfig, isKey)
        println("✅ Parent configure() completed")
    }

    override fun serialize(topic: String?, record: T?): ByteArray? {
        println("📤 Serializing record for topic: $topic")
        return super.serialize(topic, record)
    }

    override fun serialize(topic: String?, headers: Headers?, record: T?): ByteArray? {
        println("📤 Serializing record with headers for topic: $topic")
        return super.serialize(topic, headers, record)
    }
}