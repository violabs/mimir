package io.violabs.mimir.vector.weaviate.domain.request

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Request object for adding content to the vector store")
data class AddSentenceRequest(
    @field:Schema(
        description = "List of sentences to store",
        required = true
    )
    val sentences: List<String>,
    @field:Schema(
        description = "Country of request",
        required = true
    )
    val country: String = "",
    @field:Schema(
        description = "Year of request",
        required = true
    )
    val year: Int = 2025
)