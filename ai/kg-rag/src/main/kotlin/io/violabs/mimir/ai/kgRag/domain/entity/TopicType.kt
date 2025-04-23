package io.violabs.mimir.ai.kgRag.domain.entity

import org.springframework.data.neo4j.core.schema.Id
import org.springframework.data.neo4j.core.schema.Node

@Node
data class TopicType(
    @Id
    val name: String? = null
) : Entity<String> {
    override fun getKey(): String? = name
}