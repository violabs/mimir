package io.violabs.mimir.knowledgeGraph.repository

import io.violabs.mimir.core.common.Loggable
import io.violabs.mimir.knowledgeGraph.domain.Quote
import org.neo4j.ogm.session.SessionFactory
import org.springframework.stereotype.Repository
import java.util.UUID

interface QuoteRepository : DataRepository<Quote, UUID> {
    fun findByTextContainingIgnoreCase(text: String): List<Quote>
}

@Repository
class Neo4jQuoteRepository(sessionFactory: SessionFactory) :
    Neo4jDefaultRepository<Quote, UUID>(sessionFactory, Quote::class.java), QuoteRepository, Loggable {

    override fun findByTextContainingIgnoreCase(text: String): List<Quote> {
        return listOf()
    }
}