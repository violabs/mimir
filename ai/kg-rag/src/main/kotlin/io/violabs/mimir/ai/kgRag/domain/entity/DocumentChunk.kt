package io.violabs.mimir.ai.kgRag.domain.entity

import org.springframework.data.neo4j.core.schema.Id
import org.springframework.data.neo4j.core.schema.Node
import org.springframework.data.neo4j.core.schema.Relationship

@Node
data class DocumentChunk(
    val docName: String,
    val index: Int,
    val content: String,
    val sectionName: String? = null,
    @Relationship(type = "HAS", direction = Relationship.Direction.OUTGOING)
    var topics: List<Topic>? = null
) : Entity<String> {
    @Id
    private val key: String = docName + "_" + index

    override fun getKey(): String = key
}