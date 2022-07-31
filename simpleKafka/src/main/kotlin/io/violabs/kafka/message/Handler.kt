package io.violabs.kafka.message

import io.violabs.kafka.domain.Being
import mu.KLogging
import org.springframework.stereotype.Component

@Component
class Handler {
  fun handleBeing(being: Being) {
    logger.info { being }
  }

  companion object : KLogging()
}