package io.violabs.mimir.knowledgeGraph.repository

interface DataRepository<T, KEY> {
    fun save(item: T)

    fun findById(id: KEY): T?
}