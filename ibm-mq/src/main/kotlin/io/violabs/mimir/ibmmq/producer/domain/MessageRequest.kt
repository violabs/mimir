package io.violabs.mimir.ibmmq.producer.domain

data class MessageRequest(
    val content: String,
    val priority: Int = 4,
    val metadata: Map<String, String> = emptyMap()
)

data class MessageResponse(
    val messageId: String,
    val status: String,
    val timestamp: Long = System.currentTimeMillis()
)
