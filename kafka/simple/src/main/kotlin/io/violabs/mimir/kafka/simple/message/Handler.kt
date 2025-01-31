package io.violabs.mimir.kafka.simple.message

import io.violabs.mimir.kafka.simple.domain.Being
import mu.KLogging
import org.springframework.stereotype.Component

@Component
class Handler {
  fun handleBeing(being: Being) {
    logger.info { being }
  }

  companion object : KLogging()
}