package io.violabs.kafka.config

import org.apache.kafka.clients.admin.NewTopic
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer
import org.springframework.kafka.listener.DefaultErrorHandler
import org.springframework.kafka.support.converter.JsonMessageConverter
import org.springframework.kafka.support.converter.RecordMessageConverter
import org.springframework.util.backoff.FixedBackOff

@Configuration
class KafkaConfig {

  @Bean
  fun errorHandler(template: KafkaTemplate<Any, Any>): DefaultErrorHandler {
    val backoff = FixedBackOff(1000L, 2)

    val deadLetterRecoverer = DeadLetterPublishingRecoverer(template)

    return DefaultErrorHandler(deadLetterRecoverer, backoff)
  }

  @Bean
  fun converter(): RecordMessageConverter = JsonMessageConverter()

  @Bean
  fun godTopic(): NewTopic = NewTopic("god.topic", 1, 1.toShort())
}