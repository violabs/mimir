package io.violabs.mimir.knowledgeGraph.repository

import io.violabs.mimir.knowledgeGraph.domain.Quote
import org.springframework.data.neo4j.repository.query.Query
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Flux
import java.util.*

interface QuoteRepository : ReactiveCrudRepository<Quote, UUID> {
    @Query("MATCH (k:Keyword {name: \$name})-[r:TAGGED]->(q:Quote) RETURN k, r, q")
    fun findByKeyword(name: String): Flux<Quote>
}