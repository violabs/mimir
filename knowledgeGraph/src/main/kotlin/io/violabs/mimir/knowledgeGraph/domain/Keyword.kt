package io.violabs.mimir.knowledgeGraph.domain

import org.springframework.data.annotation.Id
import org.springframework.data.annotation.Version
import org.springframework.data.neo4j.core.schema.Node

@Node("Keyword")
data class Keyword(
    @Id
    val name: String? = null,
    @Version
    var version: Long? = null
) : Entity<String> {
    override fun getKey(): String? = name
}