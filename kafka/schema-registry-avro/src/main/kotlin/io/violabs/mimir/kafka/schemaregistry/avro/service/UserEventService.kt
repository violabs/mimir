package io.violabs.mimir.kafka.schemaregistry.avro.service

import com.example.avro.UserEvent
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.support.Acknowledgment
import org.springframework.kafka.support.KafkaHeaders
import org.springframework.messaging.handler.annotation.Header
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class UserEventService(
    private val kafkaTemplate: KafkaTemplate<String, Any>
) {
    
    private val logger = LoggerFactory.getLogger(UserEventService::class.java)
    
    companion object {
        const val TOPIC_NAME = "user-events"
    }

    fun sendUserEvent(userId: String, action: String, email: String? = null) {
        val userEvent = UserEvent.newBuilder()
            .setUserId(userId)
            .setAction(action)
            .setTimestamp(Instant.now().toEpochMilli())
            .apply { 
                if (email != null) {
                    setEmail(email)
                }
            }
            .build()

        kafkaTemplate.send(TOPIC_NAME, userId, userEvent)
            .whenComplete { result, failure ->
                if (failure == null) {
                    logger.info("Sent user event: userId={}, action={}", userId, action)
                } else {
                    logger.error("Failed to send user event: userId={}, action={}", userId, action, failure)
                }
            }
    }

    @KafkaListener(topics = [TOPIC_NAME], groupId = "user-event-consumer")
    fun handleUserEvent(
        @Payload userEvent: UserEvent,
        @Header(KafkaHeaders.RECEIVED_TOPIC) topic: String,
        @Header(KafkaHeaders.RECEIVED_PARTITION) partition: Int,
        @Header(KafkaHeaders.OFFSET) offset: Long,
        acknowledgment: Acknowledgment
    ) {
        try {
            logger.info(
                "Received user event: userId={}, action={}, timestamp={}, email={} from topic={}, partition={}, offset={}",
                userEvent.userId,
                userEvent.action,
                userEvent.timestamp,
                userEvent.email,
                topic,
                partition,
                offset
            )
            
            // Process the event here
            processUserEvent(userEvent)
            
            acknowledgment.acknowledge()
        } catch (e: Exception) {
            logger.error("Error processing user event", e)
            // Don't acknowledge - message will be retried
        }
    }
    
    private fun processUserEvent(userEvent: UserEvent) {
        // Business logic for processing user events
        when (userEvent.action.toString()) {
            "USER_CREATED" -> handleUserCreated(userEvent)
            "USER_UPDATED" -> handleUserUpdated(userEvent)
            "USER_DELETED" -> handleUserDeleted(userEvent)
            else -> logger.warn("Unknown action: {}", userEvent.action)
        }
    }
    
    private fun handleUserCreated(userEvent: UserEvent) {
        logger.info("Processing user creation for userId: {}", userEvent.userId)
        // Add user creation logic
    }
    
    private fun handleUserUpdated(userEvent: UserEvent) {
        logger.info("Processing user update for userId: {}", userEvent.userId)
        // Add user update logic
    }
    
    private fun handleUserDeleted(userEvent: UserEvent) {
        logger.info("Processing user deletion for userId: {}", userEvent.userId)
        // Add user deletion logic
    }
}