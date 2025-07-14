package io.violabs.mimir.schemaregistry.jsonschema.controller

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("schemas")
class SchemaController(private val objectMapper: ObjectMapper) {

    @PutMapping("convert", produces = [MediaType.TEXT_PLAIN_VALUE])
    fun convertSchemaFromSchemaRegistry(@RequestBody schemaRegistryRequest: String): String {
        val jsonNode = objectMapper.readTree(schemaRegistryRequest)
        val schemaString = jsonNode.get("schema").asText()
        val schemaNode = objectMapper.readTree(schemaString)
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(schemaNode)
    }
}