package io.violabs.mimir.schemaregistry.jsonschema.config.serde

import io.confluent.kafka.schemaregistry.client.CachedSchemaRegistryClient
import io.confluent.kafka.schemaregistry.json.SpecificationVersion
import io.confluent.kafka.serializers.AbstractKafkaSchemaSerDeConfig
import io.confluent.kafka.serializers.json.AbstractKafkaJsonSchemaSerializer
import io.confluent.kafka.serializers.json.KafkaJsonSchemaSerializer
import io.confluent.kafka.serializers.json.KafkaJsonSchemaSerializerConfig
import jakarta.annotation.PostConstruct
import org.apache.kafka.common.serialization.Serializer
import org.springframework.stereotype.Component

class CustomJsonSchemaSerializer3<T> : AbstractKafkaJsonSchemaSerializer<T?>(), Serializer<T?> {
    private lateinit var internalSerializer: KafkaJsonSchemaSerializer<T?>

    init {
        val configMap = mapOf<String, Any>(
            AbstractKafkaSchemaSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG to "http://localhost:8090",
            AbstractKafkaSchemaSerDeConfig.AUTO_REGISTER_SCHEMAS to true,
            AbstractKafkaSchemaSerDeConfig.USE_LATEST_VERSION to true,
            KafkaJsonSchemaSerializerConfig.FAIL_INVALID_SCHEMA to true,
            KafkaJsonSchemaSerializerConfig.WRITE_DATES_AS_ISO8601 to true,
            KafkaJsonSchemaSerializerConfig.LATEST_COMPATIBILITY_STRICT to true,
            KafkaJsonSchemaSerializerConfig.SCHEMA_SPEC_VERSION to SpecificationVersion.DRAFT_2020_12.toString()
        )
        val config = KafkaJsonSchemaSerializerConfig(configMap)
        configure(config)
        val schemaRegistryClient = CachedSchemaRegistryClient(
            "http://localhost:8090",
            100,
            config.originals()
        )
        internalSerializer = KafkaJsonSchemaSerializer(schemaRegistryClient, configMap)
    }

    override fun close() {
        internalSerializer.close()
    }

    override fun serialize(topic: String?, data: T?): ByteArray? {
        return internalSerializer.serialize(topic, data)
    }
}

@Component
class SerializerInstantiationTest {

    @PostConstruct
    fun testSerializerInstantiation() {
        println("🧪 Testing CustomJsonSchemaSerializer instantiation...")

        try {
            // Test 1: Class.forName
            val clazz =
                Class.forName("io.violabs.mimir.schemaregistry.jsonschema.config.serde.CustomJsonSchemaSerializer")
            println("✅ Class found via Class.forName: $clazz")

            // Test 2: Direct instantiation
            val instance = clazz.getDeclaredConstructor().newInstance()
            println("✅ Instance created via reflection: $instance")

            // Test 3: Kotlin class reference
            val kotlinClass =
                CustomJsonSchemaSerializer::class.java
            println("✅ Kotlin class reference: $kotlinClass")

            // Test 4: Create with no-arg constructor (like Kafka does)
            val kafkaStyleInstance = kotlinClass.getDeclaredConstructor().newInstance()
            println("✅ Kafka-style instantiation successful: $kafkaStyleInstance")

        } catch (e: Exception) {
            println("❌ Error during instantiation test:")
            e.printStackTrace()
        }
    }
}