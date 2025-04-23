package io.violabs.mimir.ai.kgRag.domain

interface DataIngestDocument {
    val title: String
    val content: String?
    val topics: List<String>?
}