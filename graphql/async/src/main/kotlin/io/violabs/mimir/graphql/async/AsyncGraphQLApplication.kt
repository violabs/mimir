package io.violabs.mimir.graphql.async

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class AsyncGraphQLApplication

fun main(args: Array<String>) {
  runApplication<AsyncGraphQLApplication>(*args)
    .beanDefinitionNames
    .sorted()
    .forEach(::println)
}