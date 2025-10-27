package io.violabs.mimir.ibmmq.producer.listener

import jakarta.jms.Message
import jakarta.jms.TextMessage
import mu.KotlinLogging
import org.springframework.context.annotation.Profile
import org.springframework.jms.annotation.JmsListener
import org.springframework.stereotype.Component

private val logger = KotlinLogging.logger {}

@Component
@Profile(value = ["default", "consumer"])
class QueueMessageListener {

    @JmsListener(destination = "\${mq.queue-name}")
    fun onMessage(message: Message) = try {
        when (message) {
            is TextMessage -> {
                val text = message.text
                val providerId = message.jmsMessageID
                val priority = message.jmsPriority
                val customId = message.getStringProperty("mimirMessageId")

                logger.info {
                    "Received message: providerId=$providerId mimirId=$customId priority=$priority content='${
                        text.take(
                            200
                        )
                    }'"
                }
            }

            else -> {
                logger.info { "Received non-text JMS message of type: ${message.javaClass.name}" }
            }
        }
    } catch (e: Exception) {
        logger.error(e) { "Error processing incoming JMS message" }
    }
}

