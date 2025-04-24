package io.violabs.mimir.ai.kgRag.domain.entity

import org.springframework.data.neo4j.core.schema.Id
import org.springframework.data.neo4j.core.schema.Node
import org.springframework.data.neo4j.core.schema.Relationship

@Node
data class ProcessDoc(
    @Id
    val title: String,
    @Relationship(type = "CONSISTS_OF", direction = Relationship.Direction.OUTGOING)
    var chunks: List<DocumentChunk>? = null
) : Entity<String> {
    override fun getKey(): String = title

    fun toFlattenedString(): String {
        return "ProcessDoc(title=$title, chunks=${chunks?.joinToString(",") { it.toFlattenedString() }})"
    }
}