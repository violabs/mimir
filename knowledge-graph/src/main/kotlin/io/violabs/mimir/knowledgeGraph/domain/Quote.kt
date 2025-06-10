package io.violabs.mimir.knowledgeGraph.domain

import org.springframework.data.annotation.Id
import org.springframework.data.annotation.Version
import org.springframework.data.neo4j.core.schema.GeneratedValue
import org.springframework.data.neo4j.core.schema.GeneratedValue.UUIDGenerator
import org.springframework.data.neo4j.core.schema.Node
import org.springframework.data.neo4j.core.schema.Relationship
import java.util.UUID

@Node
data class Quote(
    @Id @GeneratedValue(UUIDGenerator::class)
    val quoteId: UUID? = null,
    val text: String,
    @Relationship(type = "TAGGED", direction = Relationship.Direction.INCOMING)
    val keywords: List<Keyword> = listOf(),
    @Version
    var version: Long? = null
) : Entity<UUID> {
    override fun getKey(): UUID? = quoteId
}