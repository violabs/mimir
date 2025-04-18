package io.violabs.mimir.knowledgeGraph.domain

import org.neo4j.ogm.annotation.GeneratedValue
import org.neo4j.ogm.annotation.Id
import org.neo4j.ogm.annotation.NodeEntity
import org.neo4j.ogm.annotation.Relationship
import org.neo4j.ogm.id.UuidStrategy
import java.util.UUID

@NodeEntity
data class Quote(
    @Id @GeneratedValue(strategy = UuidStrategy::class)
    val quoteId: UUID? = null,
    val id: Long? = null,
    val text: String,
    @Relationship(type = "TAGGED", direction = Relationship.Direction.INCOMING)
    val keywords: List<Keyword> = listOf()
)