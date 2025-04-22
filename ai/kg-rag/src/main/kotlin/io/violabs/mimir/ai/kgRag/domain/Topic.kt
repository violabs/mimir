package io.violabs.mimir.ai.kgRag.domain

data class Topic(
    val name: String,
    val type: String,
    var endCharIndices: List<Int>
)