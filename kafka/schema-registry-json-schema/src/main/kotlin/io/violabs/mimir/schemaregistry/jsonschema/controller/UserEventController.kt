package io.violabs.mimir.schemaregistry.jsonschema.controller

import io.violabs.mimir.schemaregistry.jsonschema.domain.UserEventV1
import io.violabs.mimir.schemaregistry.jsonschema.domain.CreateUserEventRequest
import io.violabs.mimir.schemaregistry.jsonschema.event.UserEventProducer
import io.violabs.mimir.schemaregistry.jsonschema.service.SchemaRegistryService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/user-events")
class UserEventController(
    private val userEventProducer: UserEventProducer,
    private val schemaRegistryService: SchemaRegistryService
) {

    @PostMapping("/v1")
    fun createUserEvent(@RequestBody request: CreateUserEventRequest): ResponseEntity<UserEventV1> {
        val userEvent = UserEventV1(
            userId = request.userId,
            eventType = request.eventType,
            userData = request.userData,
            metadata = request.metadata
        )

        userEventProducer.sendUserEvent(userEvent)
        return ResponseEntity.ok(userEvent)
    }

    @PostMapping("/batch")
    fun createBatchUserEvents(@RequestBody requests: List<CreateUserEventRequest>): ResponseEntity<List<UserEventV1>> {
        val events = requests.map { request ->
            UserEventV1(
                userId = request.userId,
                eventType = request.eventType,
                userData = request.userData,
                metadata = request.metadata
            )
        }

        userEventProducer.sendUserEvents(events)
        return ResponseEntity.ok(events)
    }

    @GetMapping("/schemas")
    fun getSchemas(): ResponseEntity<List<String>> {
        return ResponseEntity.ok(schemaRegistryService.getAllSubjects())
    }

    @GetMapping("/schemas/{subject}")
    fun getSchema(@PathVariable subject: String): ResponseEntity<String> {
        val schema = schemaRegistryService.getLatestSchema(subject)
        return if (schema != null) {
            ResponseEntity.ok(schema)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @GetMapping("/schemas/{subject}/versions")
    fun getSchemaVersions(@PathVariable subject: String): ResponseEntity<List<Int>> {
        return ResponseEntity.ok(schemaRegistryService.getSchemaVersions(subject))
    }

    @GetMapping("/schemas/{subject}/versions/{version}")
    fun getSchemaByVersion(
        @PathVariable subject: String,
        @PathVariable version: Int
    ): ResponseEntity<String> {
        val schema = schemaRegistryService.getSchemaByVersion(subject, version)
        return if (schema != null) {
            ResponseEntity.ok(schema)
        } else {
            ResponseEntity.notFound().build()
        }
    }
}