package io.violabs.mimir.vector.weaviate.service

import io.violabs.mimir.vector.weaviate.domain.request.AddSentenceRequest
import org.springframework.ai.document.Document
import org.springframework.ai.vectorstore.SearchRequest
import org.springframework.ai.vectorstore.VectorStore
import org.springframework.stereotype.Service

@Service
class DataService(private val vectorStore: VectorStore) {

    fun addContent(request: AddSentenceRequest) {
        val (sentences, country, year) = request

        sentences
            .asSequence()
            .map { Document(it, mapOf("country" to country, "year" to year)) }
            .onEach { document -> println("Adding document: $document") }
            .let { documents -> vectorStore.add(documents.toList())}
    }

    fun search(request: SearchRequest): List<Document> {
        return vectorStore.similaritySearch(request)?.toList() ?: emptyList()
    }

    fun search(query: String): List<Document> {
        return vectorStore.similaritySearch(query)?.toList() ?: emptyList()
    }
}