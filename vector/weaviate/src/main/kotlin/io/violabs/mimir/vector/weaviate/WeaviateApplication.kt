package io.violabs.mimir.vector.weaviate

import io.violabs.mimir.vector.weaviate.config.WeaviateConfigProperties
import org.springframework.ai.autoconfigure.vectorstore.weaviate.WeaviateVectorStoreAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@EnableConfigurationProperties(WeaviateConfigProperties::class)
@SpringBootApplication(exclude = [WeaviateVectorStoreAutoConfiguration::class])
class WeaviateApplication

fun main(args: Array<String>) {
    runApplication<WeaviateApplication>(*args)
        .beanDefinitionNames
        .sorted()
        .forEach(::println)
}