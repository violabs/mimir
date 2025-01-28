package io.violabs.mimir.kafka.simple.controller

import io.violabs.mimir.kafka.simple.config.KafkaProperties
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("config")
class ConfigController(private val kafkaProperties: KafkaProperties) {

  @GetMapping("kafka")
  fun getKafkaConfigProperties(): KafkaProperties = kafkaProperties
}