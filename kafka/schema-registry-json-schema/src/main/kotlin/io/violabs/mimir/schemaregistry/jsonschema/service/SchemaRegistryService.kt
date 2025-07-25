package io.violabs.mimir.schemaregistry.jsonschema.service

import io.confluent.kafka.schemaregistry.client.SchemaRegistryClient
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class SchemaRegistryService(private var schemaRegistryClient: SchemaRegistryClient) {

    private val logger = LoggerFactory.getLogger(SchemaRegistryService::class.java)

    fun getAllSubjects(): List<String> {
        return try {
            schemaRegistryClient.allSubjects.toList().onEach { subject ->
                logger.info("Found subject: $subject")
            }
        } catch (e: Exception) {
            logger.error("Failed to retrieve subjects", e)
            emptyList()
        }
    }

    fun getLatestSchema(subject: String): String? {
        return try {
            val metadata = schemaRegistryClient.getLatestSchemaMetadata(subject)
            metadata.schema
        } catch (e: Exception) {
            logger.warn("Could not retrieve latest schema for subject: $subject", e)
            null
        }
    }

    fun getSchemaVersions(subject: String): List<Int> {
        return try {
            schemaRegistryClient.getAllVersions(subject)
        } catch (e: Exception) {
            logger.error("Failed to get versions for subject: $subject", e)
            emptyList()
        }
    }

    fun getSchemaByVersion(subject: String, version: Int): String? {
        return try {
            val metadata = schemaRegistryClient.getSchemaMetadata(subject, version)
            metadata.schema
        } catch (e: Exception) {
            logger.error("Failed to get schema for subject: $subject, version: $version", e)
            null
        }
    }
}