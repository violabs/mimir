package io.violabs.mimir.ai.kgRag.service

import io.violabs.mimir.ai.kgRag.domain.AddTextBlockRequest
import io.violabs.mimir.core.common.Loggable
import org.springframework.ai.document.Document
import org.springframework.ai.vectorstore.SearchRequest
import org.springframework.ai.vectorstore.VectorStore
import org.springframework.stereotype.Service

@Service
class DataService(private val vectorStore: VectorStore) : Loggable {

    fun addContent(request: AddTextBlockRequest) {
        val (blocks, country, year, author, title) = request

        blocks
            .asSequence()
            .map { Document(it, mapOf("country" to country, "year" to year, "author" to author, "title" to title)) }
            .onEach { document -> log.debug("Adding document: $document") }
            .let { documents -> vectorStore.add(documents.toList())}
    }

    fun search(request: SearchRequest): List<Document> {
        return vectorStore.similaritySearch(request)?.toList() ?: emptyList()
    }

    fun search(query: String): List<Document> {
        return vectorStore.similaritySearch(query)?.toList() ?: emptyList()
    }
}