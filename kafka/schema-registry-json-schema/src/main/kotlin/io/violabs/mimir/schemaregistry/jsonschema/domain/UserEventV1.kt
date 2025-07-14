package io.violabs.mimir.schemaregistry.jsonschema.domain


import com.fasterxml.jackson.annotation.JsonFormat
import com.kjetland.jackson.jsonSchema.annotations.JsonSchemaTitle
import java.time.Instant
import java.util.*

/**
 * User Event - Main event object for testing JSON Schema evolution
 * Schema will be auto-generated and registered by Confluent serializer
 */
//@JsonSchemaTitle("UserEventV1")
data class UserEventV1(
    override val id: String = UUID.randomUUID().toString(),
    override val userId: String,
    override val eventType: EventType,
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    override val timestamp: Instant = Instant.now(),
    override val userData: UserData? = null,
    override val metadata: Map<String, Any> = emptyMap()
) : UserEvent