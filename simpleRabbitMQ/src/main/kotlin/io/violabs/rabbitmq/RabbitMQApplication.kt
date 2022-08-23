package io.violabs.rabbitmq

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class RabbitMQApplication

fun main(args: Array<String>) {
  runApplication<RabbitMQApplication>(*args)
    .beanDefinitionNames
    .sorted()
    .forEach(::println)
}