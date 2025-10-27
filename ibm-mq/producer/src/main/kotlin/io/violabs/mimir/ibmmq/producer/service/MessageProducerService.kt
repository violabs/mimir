package io.violabs.mimir.ibmmq.producer.service

import io.violabs.mimir.ibmmq.producer.domain.MessageRequest
import io.violabs.mimir.ibmmq.producer.domain.MessageResponse
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.jms.core.JmsTemplate
import org.springframework.stereotype.Service
import java.util.*

private val logger = KotlinLogging.logger {}

@Service
class MessageProducerService(
    private val jmsTemplate: JmsTemplate
) {

    @Value("\${mq.queue-name}")
    private lateinit var queueName: String

    fun sendMessage(request: MessageRequest): MessageResponse {
        val messageId = UUID.randomUUID().toString()

        logger.info { "Sending message to queue: $queueName with ID: $messageId" }

        try {
            jmsTemplate.send(queueName) { session ->
                val message = session.createTextMessage(request.content)

                // Do NOT set JMSMessageID manually; the provider assigns it.
                // Attach our generated id as an application property instead.
                message.setStringProperty("mimirMessageId", messageId)

                // Set desired priority; provider may override unless QoS is enabled.
                message.jmsPriority = request.priority

                // Add custom properties from metadata
                request.metadata.forEach { (key, value) ->
                    message.setStringProperty(key, value)
                }

                logger.debug { "Message created with priority: ${request.priority}" }
                message
            }

            logger.info { "Message sent successfully: $messageId" }
            return MessageResponse(
                messageId = messageId,
                status = "SENT"
            )
        } catch (e: Exception) {
            logger.error(e) { "Failed to send message: $messageId" }
            return MessageResponse(
                messageId = messageId,
                status = "FAILED"
            )
        }
    }

    fun sendBatchMessages(requests: List<MessageRequest>): List<MessageResponse> {
        logger.info { "Sending batch of ${requests.size} messages" }
        return requests.map { sendMessage(it) }
    }
}
