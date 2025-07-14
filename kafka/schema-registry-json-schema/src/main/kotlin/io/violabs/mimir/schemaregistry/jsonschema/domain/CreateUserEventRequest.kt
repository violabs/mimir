package io.violabs.mimir.schemaregistry.jsonschema.domain

data class CreateUserEventRequest(
    val userId: String,
    val eventType: EventType,
    val userData: UserData? = null,
    val metadata: Map<String, Any> = emptyMap()
)