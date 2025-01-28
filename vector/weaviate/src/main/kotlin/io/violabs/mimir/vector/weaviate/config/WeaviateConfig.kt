package io.violabs.mimir.vector.weaviate.config

import org.springframework.ai.embedding.EmbeddingClient
import org.springframework.ai.vectorstore.VectorStore
import org.springframework.ai.vectorstore.WeaviateVectorStore
import org.springframework.ai.vectorstore.WeaviateVectorStore.WeaviateVectorStoreConfig
import org.springframework.ai.vectorstore.WeaviateVectorStore.WeaviateVectorStoreConfig.ConsistentLevel
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class WeaviateConfig {

    @Bean
    fun vectorStore(embeddingClient: EmbeddingClient?): VectorStore {
        val config = WeaviateVectorStoreConfig
            .builder()
            .withScheme("http")
            .withHost("localhost:8083") // Define the metadata fields to be used
            // in the similarity search filters.
            .withFilterableMetadataFields(
                listOf(
                    WeaviateVectorStoreConfig.MetadataField.text("country"),
                    WeaviateVectorStoreConfig.MetadataField.number("year"),
                    WeaviateVectorStoreConfig.MetadataField.bool("active")
                )
            ) // Consistency level can be: ONE, QUORUM, or ALL.
            .withConsistencyLevel(ConsistentLevel.ONE)
            .build()

        return WeaviateVectorStore(config, embeddingClient)
    }
}