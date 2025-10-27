package io.violabs.mimir.ibmmq.producer.controller

import io.violabs.mimir.ibmmq.producer.domain.MessageRequest
import io.violabs.mimir.ibmmq.producer.domain.MessageResponse
import io.violabs.mimir.ibmmq.producer.service.MessageProducerService
import mu.KotlinLogging
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

private val logger = KotlinLogging.logger {}

@RestController
@RequestMapping("/api/messages")
class MessageProducerController(
    private val messageProducerService: MessageProducerService
) {

    @PostMapping
    fun sendMessage(@RequestBody request: MessageRequest): ResponseEntity<MessageResponse> {
        logger.info { "Received request to send message: ${request.content.take(50)}" }
        val response = messageProducerService.sendMessage(request)

        return if (response.status == "SENT") {
            ResponseEntity.ok(response)
        } else {
            ResponseEntity.internalServerError().body(response)
        }
    }

    @PostMapping("/batch")
    fun sendBatchMessages(@RequestBody requests: List<MessageRequest>): ResponseEntity<List<MessageResponse>> {
        logger.info { "Received request to send ${requests.size} messages" }
        val responses = messageProducerService.sendBatchMessages(requests)

        val allSuccess = responses.all { it.status == "SENT" }
        return if (allSuccess) {
            ResponseEntity.ok(responses)
        } else {
            ResponseEntity.status(207).body(responses) // Multi-Status
        }
    }

    @GetMapping("/health")
    fun health(): ResponseEntity<Map<String, String>> {
        return ResponseEntity.ok(
            mapOf(
                "status" to "UP",
                "service" to "IBM MQ Producer",
                "timestamp" to System.currentTimeMillis().toString()
            )
        )
    }
}
