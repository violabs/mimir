package io.violabs.mimir.kafka.simple.message

import io.violabs.mimir.kafka.simple.domain.God
import io.violabs.mimir.kafka.simple.domain.Monster
import mu.KLogging
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component
import java.util.concurrent.CountDownLatch

@Component
class GodListener(private val handler: Handler) {

  val latch = CountDownLatch(1)

  @KafkaListener(id = "godGroup", topics = ["god.topic"])
  fun listen(god: God) {
    println(RECEIVED_TEMPLATE.format(god))

    if (god.name?.isNotBlank() == true) {
      latch.countDown()
      handler.handleBeing(god)
    }

    throw Exception("No valid god was provided!")
  }

  @KafkaListener(id = "godDLTGroup", topics = ["god.topic.DLT"])
  fun listen(info: String) {
    logger.info { RECEIVED_DLT_TEMPLATE.format(info) }
  }

  companion object : KLogging() {
    const val RECEIVED_TEMPLATE = "Received god %s"
    const val RECEIVED_DLT_TEMPLATE = "Received god DLT: %s"
  }
}

@Component
class MonsterListener(private val handler: Handler) {

  @KafkaListener(id = "monsterGroup", topics = ["monster.topic"])
  fun listen(monster: Monster) {
    logger.info { RECEIVED_TEMPLATE.format(monster) }

    if (monster.name?.isNotBlank() == true) return handler.handleBeing(monster)

    throw Exception("No valid monster was provided!")
  }

  @KafkaListener(id = "monsterDLTGroup", topics = ["monster.topic.DLT"])
  fun listen(info: String) {
    logger.info { RECEIVED_DLT_TEMPLATE.format(info) }
  }

  companion object : KLogging() {
    const val RECEIVED_TEMPLATE = "Received monster %s"
    const val RECEIVED_DLT_TEMPLATE = "Received monster DLT: %s"
  }
}