package io.violabs.weaviate

import org.springframework.ai.autoconfigure.vectorstore.weaviate.WeaviateVectorStoreAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(exclude = [WeaviateVectorStoreAutoConfiguration::class])
class MilvusApplication

fun main(args: Array<String>) {
    runApplication<MilvusApplication>(*args)
        .beanDefinitionNames
        .sorted()
        .forEach(::println)
}