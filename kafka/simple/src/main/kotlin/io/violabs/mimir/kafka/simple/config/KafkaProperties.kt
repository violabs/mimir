package io.violabs.mimir.kafka.simple.config

import org.apache.kafka.clients.admin.NewTopic
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.util.backoff.FixedBackOff

@ConfigurationProperties(prefix = "app.topics")
class KafkaProperties(
    var error: ErrorHandleProperties,
    var gods: TopicProperties,
    var monsters: TopicProperties
) {

  class TopicProperties(
    var topicName: String,
    var partitions: Int,
    var replications: Short
  ) {
    fun toNewTopic(): NewTopic = NewTopic(topicName, partitions, replications)
  }

  class ErrorHandleProperties(
    var interval: Long,
    var maxRetry: Long
  ) {
    fun toFixedBackOff(): FixedBackOff = FixedBackOff(interval, maxRetry)
  }
}