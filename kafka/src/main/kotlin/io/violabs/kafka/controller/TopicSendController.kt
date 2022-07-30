package io.violabs.kafka.controller

import io.violabs.kafka.config.KafkaProperties
import io.violabs.kafka.domain.God
import org.springframework.http.ResponseEntity
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("send")
class TopicSendController(
  private val kafkaProperties: KafkaProperties,
  private val kafkaTemplate: KafkaTemplate<Any, Any>
) {

  @PostMapping("gods/{name}")
  fun sendProse(@PathVariable name: String): ResponseEntity<String> {
    kafkaTemplate.send(kafkaProperties.gods.topicName, God(name))
    return ResponseEntity.ok("Send event for god: $name")
  }
}