package io.violabs.mimir.schemaregistry.jsonschema.event

import io.violabs.mimir.schemaregistry.jsonschema.domain.EventType
import io.violabs.mimir.schemaregistry.jsonschema.domain.UserEventV1
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.Acknowledgment
import org.springframework.kafka.support.KafkaHeaders
import org.springframework.messaging.handler.annotation.Header
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Service

@Service
class UserEventConsumer {

    private val logger = LoggerFactory.getLogger(UserEventConsumer::class.java)

    @KafkaListener(
        topics = ["user-events"],
        groupId = "json-schema-demo-v1",
        containerFactory = "kafkaListenerContainerFactory"
    )
    fun handleUserEvent(
        @Payload payload: ConsumerRecord<String, UserEventV1>,
        @Header(KafkaHeaders.RECEIVED_TOPIC) topic: String,
        @Header(KafkaHeaders.RECEIVED_PARTITION) partition: Int,
        @Header(KafkaHeaders.OFFSET) offset: Long,
        acknowledgment: Acknowledgment
    ) {
        try {
            logger.info("Received message from topic: $topic, partition: $partition, offset: $offset")

            val userEvent = payload.value()

            logger.info("Processing user event: ${userEvent.id} for user: ${userEvent.userId}")
            logger.info("Event type: ${userEvent.eventType}, timestamp: ${userEvent.timestamp}")

            processUserEvent(userEvent)
            acknowledgment.acknowledge()

        } catch (e: Exception) {
            logger.error("Error processing user event from topic: $topic", e)
        }
    }

    private fun processUserEvent(userEvent: UserEventV1) {
        when (userEvent.eventType) {
            EventType.CREATED -> {
                logger.info("Processing user creation: ${userEvent.userData?.name}")
            }

            EventType.UPDATED -> {
                logger.info("Processing user update: ${userEvent.userData?.name}")
            }

            EventType.DELETED -> {
                logger.info("Processing user deletion for ID: ${userEvent.userId}")
            }
        }
    }
}