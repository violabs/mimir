package io.violabs.mimir.knowledgeGraph.domain

import org.springframework.data.annotation.Id
import org.springframework.data.neo4j.core.schema.Node

@Node
data class Keyword(
    @Id
    val name: String? = null,
) : Entity<String> {
    override fun getKey(): String? = name
}