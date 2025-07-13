package io.violabs.mimir.schemaregistry.jsonschema.domain

data class AdditionalEventData(
    val sessionId: String? = null,
    val userAgent: String? = null,
    val ipAddress: String? = null,
    val tags: List<String> = emptyList()
)