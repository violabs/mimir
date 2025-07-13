package io.violabs.mimir.schemaregistry.jsonschema.domain

import com.fasterxml.jackson.annotation.JsonFormat
import com.kjetland.jackson.jsonSchema.annotations.JsonSchemaTitle
import java.time.Instant
import java.util.UUID

/**
 * Version 2 of UserEvent with additional fields for testing evolution
 */
@JsonSchemaTitle("UserEventV2")
data class UserEventV2(
    override val id: String = UUID.randomUUID().toString(),
    override val userId: String,
    override val eventType: EventType,
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    override val timestamp: Instant = Instant.now(),
    override val userData: UserData? = null,
    override val metadata: Map<String, Any> = emptyMap(),

    // New fields for testing evolution
    val version: String = "2.0",
    val source: String = "api",
    val correlationId: String? = null,
    val additionalData: AdditionalEventData? = null
) : UserEvent