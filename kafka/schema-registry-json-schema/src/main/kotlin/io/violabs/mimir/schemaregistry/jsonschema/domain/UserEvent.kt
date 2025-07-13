package io.violabs.mimir.schemaregistry.jsonschema.domain

import java.time.Instant

interface UserEvent {
    val id: String
    val userId: String
    val eventType: EventType
    val timestamp: Instant
    val userData: UserData?
    val metadata: Map<String, Any>
}