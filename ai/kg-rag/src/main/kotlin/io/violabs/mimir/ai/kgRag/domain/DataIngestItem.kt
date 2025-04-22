package io.violabs.mimir.ai.kgRag.domain

interface DataIngestItem {
    val title: String
    val content: String?
    val topics: List<String>?
}