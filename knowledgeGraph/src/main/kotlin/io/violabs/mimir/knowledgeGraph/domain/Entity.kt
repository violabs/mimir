package io.violabs.mimir.knowledgeGraph.domain

interface Entity<KEY> {
    fun getKey(): KEY?
}