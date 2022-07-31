package io.violabs.kafka

import io.violabs.kafka.config.KafkaProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties(KafkaProperties::class)
class KafkaApplication

fun main(args: Array<String>) {
  runApplication<KafkaApplication>(*args)
    .beanDefinitionNames
    .sorted()
    .forEach(::println)
}