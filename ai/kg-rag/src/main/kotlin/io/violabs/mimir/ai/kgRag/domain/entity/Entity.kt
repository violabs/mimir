package io.violabs.mimir.ai.kgRag.domain.entity

interface Entity<KEY> {
    fun getKey(): KEY?
}