package io.violabs.kafka.message

import io.violabs.kafka.domain.God
import mu.KLogging
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class GodListener {

  @KafkaListener(id = "godGroup", topics = ["god.topic"])
  fun listen(god: God) {
    logger.info { "Received: $god" }

    if (god.name?.contains("null") == false) return

    throw Exception("No valid god was provided!")
  }

  companion object : KLogging()
}