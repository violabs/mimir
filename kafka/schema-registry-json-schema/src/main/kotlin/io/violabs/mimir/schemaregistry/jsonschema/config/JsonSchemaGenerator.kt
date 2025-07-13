package io.violabs.mimir.schemaregistry.jsonschema.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.github.victools.jsonschema.generator.*

import org.springframework.stereotype.Component

@Component
class JsonSchemaGenerator {

    private val objectMapper = ObjectMapper().registerModule(KotlinModule.Builder().build())

    fun generateSchema(clazz: Class<*>): String {
        val configBuilder = SchemaGeneratorConfigBuilder(SchemaVersion.DRAFT_2020_12, OptionPreset.PLAIN_JSON)

        val config = configBuilder.build()
        val generator = SchemaGenerator(config)
        val jsonSchema = generator.generateSchema(clazz)

        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonSchema)
    }
}