package io.violabs.mimir.knowledgeGraph.service

import io.violabs.mimir.knowledgeGraph.domain.Keyword
import io.violabs.mimir.knowledgeGraph.domain.Quote
import io.violabs.mimir.knowledgeGraph.repository.KeywordRepository
import io.violabs.mimir.knowledgeGraph.repository.QuoteRepository
import org.springframework.stereotype.Service

@Service
class KnowledgeGraphService(
    private val keywordRepository: KeywordRepository,
    private val quoteRepository: QuoteRepository
) {
    fun addQuote(text: String, keywordNames: List<String>) {
        val keywords = keywordNames.map { name ->
            keywordRepository.findById(name) ?: Keyword(name)
        }
        val quote = Quote(text = text, keywords = keywords)
        quoteRepository.save(quote)
    }

    fun findQuotesByKeyword(keyword: String): List<Quote> {
        return quoteRepository.findByTextContainingIgnoreCase(keyword)
    }
}