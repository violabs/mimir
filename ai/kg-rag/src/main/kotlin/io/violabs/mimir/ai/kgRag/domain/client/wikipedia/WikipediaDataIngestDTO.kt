package io.violabs.mimir.ai.kgRag.domain.client.wikipedia

import io.violabs.mimir.ai.kgRag.domain.DataIngestDocument

data class WikipediaDataIngestDTO(
    override val title: String,
    override val content: String? = null,
    override val topics: List<String>? = null
) : DataIngestDocument