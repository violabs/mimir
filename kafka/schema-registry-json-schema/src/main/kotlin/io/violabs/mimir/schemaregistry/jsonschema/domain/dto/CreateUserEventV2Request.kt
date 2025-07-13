package io.violabs.mimir.schemaregistry.jsonschema.domain.dto

import io.violabs.mimir.schemaregistry.jsonschema.domain.AdditionalEventData
import io.violabs.mimir.schemaregistry.jsonschema.domain.EventType
import io.violabs.mimir.schemaregistry.jsonschema.domain.UserData

data class CreateUserEventV2Request(
    val userId: String,
    val eventType: EventType,
    val userData: UserData? = null,
    val metadata: Map<String, Any> = emptyMap(),
    val correlationId: String? = null,
    val additionalData: AdditionalEventData? = null
)