package io.violabs.mimir.ai.kgRag

import io.violabs.mimir.ai.kgRag.config.WeaviateConfigProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@EnableConfigurationProperties(WeaviateConfigProperties::class)
@SpringBootApplication
class KGRAGApplication

fun main(args: Array<String>) {
    runApplication<KGRAGApplication>(*args)
}