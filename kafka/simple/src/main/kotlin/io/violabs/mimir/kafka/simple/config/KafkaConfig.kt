package io.violabs.mimir.kafka.simple.config

import org.apache.kafka.clients.admin.NewTopic
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer
import org.springframework.kafka.listener.DefaultErrorHandler
import org.springframework.kafka.support.converter.JsonMessageConverter
import org.springframework.kafka.support.converter.RecordMessageConverter

@Configuration
class KafkaConfig(private val kafkaProperties: KafkaProperties) {

  @Bean
  fun errorHandler(template: KafkaTemplate<Any, Any>): DefaultErrorHandler {
    val deadLetterRecoverer = DeadLetterPublishingRecoverer(template)

    return DefaultErrorHandler(deadLetterRecoverer, kafkaProperties.error.toFixedBackOff())
  }

  @Bean
  fun converter(): RecordMessageConverter = JsonMessageConverter()

  @Bean
  fun godTopic(): NewTopic = kafkaProperties.gods.toNewTopic()
}