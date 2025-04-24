package io.violabs.mimir.ai.kgRag.service

import io.violabs.mimir.ai.kgRag.domain.DataIngestDocument
import io.violabs.mimir.ai.kgRag.domain.api.AddSearchContentRequest
import io.violabs.mimir.core.common.Loggable
import org.springframework.ai.document.Document
import org.springframework.ai.vectorstore.SearchRequest
import org.springframework.ai.vectorstore.VectorStore
import org.springframework.stereotype.Service

@Service
class QueryService(private val vectorStore: VectorStore) : Loggable {

    fun addContent(request: AddSearchContentRequest) = addDocs(request.blocks) {
        it to request.metadataToMap()
    }

    fun addContent(ingestionDoc: DataIngestDocument) = addDocs(ingestionDoc.doc?.chunks) {
        it.content to it.metadataToMap()
    }

    fun search(request: SearchRequest): List<Document> {
        return vectorStore.similaritySearch(request)?.toList() ?: emptyList()
    }

    private fun <T> addDocs(list: List<T>?, docExtractor: (T) -> Pair<String, Map<String, Any>>) {
        list
            ?.asSequence()
            ?.map { docExtractor(it) }
            ?.map { (content, metadata) -> Document(content, metadata) }
            ?.onEach { document -> log.debug("Adding document: $document") }
            ?.let { documents -> vectorStore.add(documents.toList()) }
    }
}