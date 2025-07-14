package io.violabs.mimir.schemaregistry.jsonschema.domain

data class SchemaRegistrySubject(
    val subject: String,
    val version: Int,
    val id: Int,
    val schema: String,
    val schemaType: String,
    val deleted: Boolean
)