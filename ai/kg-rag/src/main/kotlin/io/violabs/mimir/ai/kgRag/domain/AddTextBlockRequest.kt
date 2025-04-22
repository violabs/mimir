package io.violabs.mimir.ai.kgRag.domain

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Request object for adding content to the vector store")
data class AddTextBlockRequest(
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
)