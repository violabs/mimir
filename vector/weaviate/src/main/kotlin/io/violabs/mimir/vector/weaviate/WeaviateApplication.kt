package io.violabs.mimir.vector.weaviate

import org.springframework.ai.autoconfigure.vectorstore.weaviate.WeaviateVectorStoreAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(exclude = [WeaviateVectorStoreAutoConfiguration::class])
class WeaviateApplication

fun main(args: Array<String>) {
    runApplication<WeaviateApplication>(*args)
        .beanDefinitionNames
        .sorted()
        .forEach(::println)
}