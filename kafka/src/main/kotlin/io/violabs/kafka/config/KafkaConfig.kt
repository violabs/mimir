package io.violabs.kafka.config

import org.apache.kafka.clients.admin.NewTopic
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.support.converter.JsonMessageConverter
import org.springframework.kafka.support.converter.RecordMessageConverter

@Configuration
class KafkaConfig {

  @Bean
  fun converter(): RecordMessageConverter = JsonMessageConverter()

  @Bean
  fun godTopic(): NewTopic = NewTopic("god.topic", 1, 1.toShort())
}