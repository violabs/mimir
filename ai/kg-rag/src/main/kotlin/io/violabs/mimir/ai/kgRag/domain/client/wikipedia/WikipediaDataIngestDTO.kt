package io.violabs.mimir.ai.kgRag.domain.client.wikipedia

import io.violabs.mimir.ai.kgRag.domain.DataIngestDocument
import io.violabs.mimir.ai.kgRag.domain.entity.ProcessDoc
import io.violabs.mimir.ai.kgRag.domain.entity.Topic

data class WikipediaDataIngestDTO(
    override val title: String,
    override val content: String? = null,
    override val doc: ProcessDoc? = null,
    override val topics: List<Topic>? = null
) : DataIngestDocument