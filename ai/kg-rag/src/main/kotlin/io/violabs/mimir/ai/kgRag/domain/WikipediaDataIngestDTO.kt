package io.violabs.mimir.ai.kgRag.domain

data class WikipediaDataIngestDTO(
    override val title: String,
    override val content: String? = null,
    override val topics: List<String>? = null
) : DataIngestItem