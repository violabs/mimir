package io.violabs.mimir.schemaregistry.jsonschema.event

import io.violabs.mimir.schemaregistry.jsonschema.domain.UserEventV1
import org.slf4j.LoggerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.support.SendResult
import org.springframework.stereotype.Service
import java.util.concurrent.CompletableFuture

@Service
class UserEventProducer(
    private val kafkaTemplateV1: KafkaTemplate<String, UserEventV1>
) {

    private val logger = LoggerFactory.getLogger(UserEventProducer::class.java)

    fun sendUserEvent(userEvent: UserEventV1): CompletableFuture<SendResult<String, UserEventV1>> {
        logger.info("Sending user event: ${userEvent.id} for user: ${userEvent.userId}")

        return kafkaTemplateV1.send("user-events", userEvent.userId, userEvent)
            .whenComplete { result, exception ->
                if (exception == null) {
                    logger.info("Successfully sent event ${userEvent.id} with offset: ${result.recordMetadata.offset()}")
                } else {
                    logger.error("Failed to send event ${userEvent.id}", exception)
                }
            }
    }

    fun sendUserEvents(events: List<UserEventV1>): List<CompletableFuture<SendResult<String, UserEventV1>>> {
        return events.map { sendUserEvent(it) }
    }
}