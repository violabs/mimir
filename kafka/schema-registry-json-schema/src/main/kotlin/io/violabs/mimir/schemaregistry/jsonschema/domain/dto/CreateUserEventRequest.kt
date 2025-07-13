package io.violabs.mimir.schemaregistry.jsonschema.domain.dto

import io.violabs.mimir.schemaregistry.jsonschema.domain.EventType
import io.violabs.mimir.schemaregistry.jsonschema.domain.UserData

data class CreateUserEventRequest(
    val userId: String,
    val eventType: EventType,
    val userData: UserData? = null,
    val metadata: Map<String, Any> = emptyMap()
)