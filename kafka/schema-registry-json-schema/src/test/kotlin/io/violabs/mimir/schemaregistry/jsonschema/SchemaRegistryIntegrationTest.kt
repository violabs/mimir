package io.violabs.mimir.schemaregistry.jsonschema

import io.violabs.mimir.schemaregistry.jsonschema.domain.*
import io.violabs.mimir.schemaregistry.jsonschema.event.UserEventProducer
import io.violabs.mimir.schemaregistry.jsonschema.service.SchemaRegistryService
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertTrue
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.TestPropertySource
import java.util.concurrent.TimeUnit
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

@SpringBootTest
@DirtiesContext
@TestPropertySource("classpath:application.yml")
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class SchemaRegistryIntegrationTest {

    @Autowired
    private lateinit var userEventProducer: UserEventProducer

    @Autowired
    private lateinit var schemaRegistryService: SchemaRegistryService

    @Test
    @Order(1)
    @Timeout(30, unit = TimeUnit.SECONDS)
    fun `should register schema when sending first V1 event`() {
        // Given
        val userEvent = UserEventV1(
            userId = "schema-test-1",
            eventType = EventType.CREATED,
            userData = UserData(
                name = "Schema Test User",
                email = "schema@example.com",
                age = 30
            )
        )

        // When
        val result = userEventProducer.sendUserEvent(userEvent)
        val sendResult = result.get(10, TimeUnit.SECONDS)

        // Then
        assertNotNull(sendResult.recordMetadata)
        assertTrue(sendResult.recordMetadata.hasOffset())

        // Verify schema was registered
        Thread.sleep(2000) // Allow time for schema registration
        val subjects = schemaRegistryService.getAllSubjects()
        assertTrue(subjects.contains("user-events-value"))

        val schema = schemaRegistryService.getLatestSchema("user-events-value")
        assertNotNull(schema)
        assertTrue(schema.contains("User Event")) {
            "Expected schema to contain 'UserEvent', but got: $schema"
        }

        println("V1 schema registered: ${schema.take(200)}...")
    }

    @Test
    @Order(2)
    @Timeout(30, unit = TimeUnit.SECONDS)
    fun `should register evolved schema when sending V2 event`() {
        // Given
        val userEventV2 = UserEventV2(
            userId = "schema-test-2",
            eventType = EventType.UPDATED,
            userData = UserData(
                name = "Schema V2 User",
                email = "schemav2@example.com"
            ),
            correlationId = "schema-corr-123",
            additionalData = AdditionalEventData(
                sessionId = "schema-session",
                userAgent = "Schema-Test/1.0",
                tags = listOf("schema", "evolution")
            )
        )

        // When
        val result = userEventProducer.sendUserEventV2(userEventV2)
        val sendResult = result.get(10, TimeUnit.SECONDS)

        // Then
        assertNotNull(sendResult.recordMetadata)
        assertTrue(sendResult.recordMetadata.hasOffset())

        // Verify V2 schema was registered
        Thread.sleep(2000) // Allow time for schema registration
        val subjects = schemaRegistryService.getAllSubjects()
        assertTrue(subjects.contains("user-events-v2-value"))

        val schemaV2 = schemaRegistryService.getLatestSchema("user-events-v2-value")
        assertNotNull(schemaV2)
        assertTrue(schemaV2.contains("UserEventV2") || schemaV2.contains("correlationId"))

        println("V2 schema registered: ${schemaV2.take(200)}...")
    }

    @Test
    @Order(3)
    @Timeout(30, unit = TimeUnit.SECONDS)
    fun `should maintain schema compatibility across versions`() {
        // Given - schemas from previous tests should exist
        val subjects = schemaRegistryService.getAllSubjects()

        // Then
        assertTrue(subjects.contains("user-events-value"))
        assertTrue(subjects.contains("user-events-v2-value"))

        // Verify schema versions
        val v1Versions = schemaRegistryService.getSchemaVersions("user-events-value")
        val v2Versions = schemaRegistryService.getSchemaVersions("user-events-v2-value")

        assertTrue(v1Versions.isNotEmpty())
        assertTrue(v2Versions.isNotEmpty())

        println("V1 schema versions: $v1Versions")
        println("V2 schema versions: $v2Versions")

        // Verify we can retrieve specific versions
        val v1Schema = schemaRegistryService.getSchemaByVersion("user-events-value", 1)
        val v2Schema = schemaRegistryService.getSchemaByVersion("user-events-v2-value", 1)

        assertNotNull(v1Schema)
        assertNotNull(v2Schema)

        // V2 should have additional fields that V1 doesn't have
        assertTrue(v2Schema.contains("version") || v2Schema.contains("correlationId"))
    }

    @Test
    @Order(4)
    @Timeout(30, unit = TimeUnit.SECONDS)
    fun `should send multiple events and maintain schema consistency`() {
        // Given
        val events = listOf(
            UserEventV1(userId = "batch-1", eventType = EventType.CREATED),
            UserEventV1(userId = "batch-2", eventType = EventType.UPDATED),
            UserEventV1(userId = "batch-3", eventType = EventType.DELETED)
        )

        // When
        val results = userEventProducer.sendUserEvents(events)

        // Then
        results.forEach { future ->
            val sendResult = future.get(10, TimeUnit.SECONDS)
            assertNotNull(sendResult.recordMetadata)
            assertTrue(sendResult.recordMetadata.hasOffset())
        }

        // Verify schema registry state remains consistent
        val subjects = schemaRegistryService.getAllSubjects()
        val v1Versions = schemaRegistryService.getSchemaVersions("user-events-value")

        assertTrue(subjects.contains("user-events-value"))
        assertEquals(1, v1Versions.size) // Should still be version 1

        println("Schema consistency maintained after batch operations")
    }
}