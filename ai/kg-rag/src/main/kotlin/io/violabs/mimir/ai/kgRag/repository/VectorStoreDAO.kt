package io.violabs.mimir.ai.kgRag.repository

import io.violabs.mimir.ai.kgRag.domain.api.AddTextBlockRequest
import io.violabs.mimir.core.common.Loggable
import org.springframework.ai.document.Document
import org.springframework.ai.vectorstore.SearchRequest
import org.springframework.ai.vectorstore.VectorStore
import org.springframework.stereotype.Service

@Service
class VectorStoreDAO(private val vectorStore: VectorStore) : Loggable {

    fun addContent(request: AddTextBlockRequest) {
        val (blocks, title) = request

        blocks
            .asSequence()
            .map { Document(it, mapOf("title" to title)) }
            .onEach { document -> log.debug("Adding document: $document") }
            .let { documents -> vectorStore.add(documents.toList())}
    }

    fun search(request: SearchRequest): List<Document> {
        return vectorStore.similaritySearch(request)?.toList() ?: emptyList()
    }
}