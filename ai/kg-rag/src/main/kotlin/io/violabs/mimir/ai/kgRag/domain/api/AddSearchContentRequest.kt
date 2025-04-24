package io.violabs.mimir.ai.kgRag.domain.api

import io.swagger.v3.oas.annotations.media.Schema
import io.violabs.mimir.ai.kgRag.domain.VectorMetadataKey

@Schema(description = "Request object for adding content to the vector store")
data class AddSearchContentRequest(
    @field:Schema(
        description = "List of text blocks to store",
        required = true
    )
    val blocks: List<String>,
    @field:Schema(
        description = "Title of work",
        required = true
    )
    val title: String,
    @field:Schema(
        description = "Related topics",
        required = true
    )
    val topics: List<String>,
    @field:Schema(
        description = "Index in article",
        required = true
    )
    val index: Int
) {
    fun metadataToMap(): Map<String, Any> {
        return mapOf(
            VectorMetadataKey.TITLE to title,
            VectorMetadataKey.TOPICS to topics,
            VectorMetadataKey.INDEX to index
        )
    }
}