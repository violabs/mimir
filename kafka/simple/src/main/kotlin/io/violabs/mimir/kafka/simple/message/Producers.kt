package io.violabs.mimir.kafka.simple.message

import io.violabs.mimir.kafka.simple.config.KafkaProperties
import io.violabs.mimir.kafka.simple.domain.God
import io.violabs.mimir.kafka.simple.domain.Monster
import org.springframework.http.ResponseEntity
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
class GodProducer(
    private val kafkaProperties: KafkaProperties,
    private val kafkaTemplate: KafkaTemplate<Any, Any>
) {
  fun send(name: String): ResponseEntity<String> {
    kafkaTemplate.send(kafkaProperties.gods.topicName, God(name))
    return ResponseEntity.ok("Sent event for god: $name")
  }
}

@Component
class MonsterProducer(
    private val kafkaProperties: KafkaProperties,
    private val kafkaTemplate: KafkaTemplate<Any, Any>
) {
  fun send(name: String): ResponseEntity<String> {
    kafkaTemplate.send(kafkaProperties.monsters.topicName, Monster(name))
    return ResponseEntity.ok("Sent event for monster: $name")
  }
}