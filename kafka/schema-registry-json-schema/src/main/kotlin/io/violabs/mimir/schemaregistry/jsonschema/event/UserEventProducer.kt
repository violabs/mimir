package io.violabs.mimir.schemaregistry.jsonschema.event

import io.violabs.mimir.schemaregistry.jsonschema.domain.UserEventV1
import org.slf4j.LoggerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.support.SendResult
import org.springframework.stereotype.Service
import java.util.concurrent.CompletableFuture

@Service
class UserEventProducer(
    private val kafkaTemplateV1: KafkaTemplate<String, UserEventV1>,
//    private val kafkaTemplateV2: KafkaTemplate<String, UserEventV2>,
) {

    private val logger = LoggerFactory.getLogger(UserEventProducer::class.java)

    fun sendUserEvent(userEvent: UserEventV1): CompletableFuture<SendResult<String, UserEventV1>> {
        logger.info("Sending user event: ${userEvent.id} for user: ${userEvent.userId}")

        return kafkaTemplateV1.send("json-schema-demo-v1", userEvent.userId, userEvent)
            .whenComplete { result, exception ->
                if (exception == null) {
                    logger.info("Successfully sent event ${userEvent.id} with offset: ${result.recordMetadata.offset()}")
                } else {
                    logger.error("Failed to send event ${userEvent.id}", exception)
                }
            }
    }
//
//    fun sendUserEventV2(userEvent: UserEventV2): CompletableFuture<SendResult<String, UserEventV2>> {
//        logger.info("Sending user event V2: ${userEvent.id} for user: ${userEvent.userId}")
//
//        return kafkaTemplateV2.send(KafkaConfig.USER_EVENT_V2_TOPIC, userEvent.userId, userEvent)
//            .whenComplete { result, exception ->
//                if (exception == null) {
//                    logger.info("Successfully sent event V2 ${userEvent.id} with offset: ${result.recordMetadata.offset()}")
//                } else {
//                    logger.error("Failed to send event V2 ${userEvent.id}", exception)
//                }
//            }
//    }

    fun sendUserEvents(events: List<UserEventV1>): List<CompletableFuture<SendResult<String, UserEventV1>>> {
        return events.map { sendUserEvent(it) }
    }
}