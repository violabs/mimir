package io.violabs.mimir.knowledgeGraph.service

import io.violabs.mimir.knowledgeGraph.domain.Keyword
import io.violabs.mimir.knowledgeGraph.domain.Quote
import io.violabs.mimir.knowledgeGraph.repository.KeywordRepository
import io.violabs.mimir.knowledgeGraph.repository.QuoteRepository
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.stereotype.Service

@Service
class KnowledgeGraphService(
    private val keywordRepository: KeywordRepository,
    private val quoteRepository: QuoteRepository
) {
    suspend fun addQuote(text: String, keywordNames: List<String>): Quote? {
        val keywords = keywordNames.map { name ->
            keywordRepository.findById(name).awaitSingleOrNull() ?: Keyword(name)
        }
        val quote = Quote(text = text, keywords = keywords)
        return quoteRepository.save(quote).awaitSingleOrNull()
    }

    suspend fun listQuotes(): List<Quote> {
        return quoteRepository.findAll().collectList().awaitSingleOrNull() ?: emptyList()
    }

    suspend fun findQuotesByKeyword(keyword: String): List<Quote> {
        return quoteRepository.findByKeyword(keyword).collectList().awaitSingleOrNull() ?: emptyList()
    }
}