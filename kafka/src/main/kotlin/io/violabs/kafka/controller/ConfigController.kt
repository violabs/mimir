package io.violabs.kafka.controller

import io.violabs.kafka.config.KafkaProperties
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("config")
class ConfigController(private val kafkaProperties: KafkaProperties) {

  @GetMapping("kafka")
  fun getKafkaConfigProperties(): KafkaProperties = kafkaProperties
}