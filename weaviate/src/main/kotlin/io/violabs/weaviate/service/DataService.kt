//package io.violabs.weaviate.service
//
//import org.springframework.ai.document.Document
//import org.springframework.ai.vectorstore.SearchRequest
//import org.springframework.ai.vectorstore.VectorStore
//import org.springframework.stereotype.Service
//
//@Service
//class DataService(private val vectorStore: VectorStore) {
//
//    fun addContent(sentences: List<String>) {
//        sentences
//            .asSequence()
//            .map { Document(it) }
//            .onEach { document -> println("Adding document: $document") }
//            .let { documents -> vectorStore.add(documents.toList())}
//    }
//
//    fun search(query: String, limit: Int): List<Document> {
//        val request = SearchRequest.query(query).withTopK(limit)
//        return vectorStore.similaritySearch(request)
//    }
//}