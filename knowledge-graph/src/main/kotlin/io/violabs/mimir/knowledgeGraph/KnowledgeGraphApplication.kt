package io.violabs.mimir.knowledgeGraph

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class KnowledgeGraphApplication

fun main(args: Array<String>) {
    runApplication<KnowledgeGraphApplication>(*args)
        .beanDefinitionNames
        .sorted()
        .forEach(::println)
}