package io.violabs.mimir.vector.weaviate.config

import io.weaviate.client.Config
import io.weaviate.client.WeaviateClient
import jakarta.annotation.PostConstruct
import org.springframework.ai.embedding.EmbeddingModel
import org.springframework.ai.vectorstore.VectorStore
import org.springframework.ai.vectorstore.weaviate.WeaviateVectorStore
import org.springframework.ai.vectorstore.weaviate.WeaviateVectorStore.ConsistentLevel
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@ConfigurationProperties(prefix = "app.vector.weaviate")
class WeaviateConfigProperties(
    val scheme: String,
    val host: String
)

@Configuration
class WeaviateConfig(val configProperties: WeaviateConfigProperties) {

    @Bean
    fun weaviateClient(): WeaviateClient {
        return WeaviateClient(Config(
            configProperties.scheme, configProperties.host
        ))
    }

    @Bean
    fun vectorStore(weaviateClient: WeaviateClient, embeddingModel: EmbeddingModel): VectorStore {
        return WeaviateVectorStore.builder(weaviateClient, embeddingModel)
            .objectClass("CustomClass") // Optional: defaults to "SpringAiWeaviate"
            .consistencyLevel(ConsistentLevel.QUORUM) // Optional: defaults to ConsistentLevel.ONE
            .filterMetadataFields(
                listOf( // Optional: fields that can be used in filters
                    WeaviateVectorStore.MetadataField.text("country"),
                    WeaviateVectorStore.MetadataField.number("year")
                )
            )
            .build()
    }

    @PostConstruct
    fun init() {
        println("Initializing Weaviate config")
        println(" - schema: ${configProperties.scheme}")
        println(" - host:   ${configProperties.host}")
    }
}