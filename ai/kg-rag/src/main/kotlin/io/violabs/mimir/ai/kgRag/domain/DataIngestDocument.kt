package io.violabs.mimir.ai.kgRag.domain

import io.violabs.mimir.ai.kgRag.domain.entity.ProcessDoc
import io.violabs.mimir.ai.kgRag.domain.entity.Topic

interface DataIngestDocument {
    val title: String
    val content: String?
    val doc: ProcessDoc?
    val topics: List<Topic>?
}