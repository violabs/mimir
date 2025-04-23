package io.violabs.mimir.ai.kgRag.domain.entity

import org.springframework.data.annotation.Version
import org.springframework.data.neo4j.core.schema.Id
import org.springframework.data.neo4j.core.schema.Node
import org.springframework.data.neo4j.core.schema.Relationship

@Node
data class Topic(
    @Id
    val name: String,
    var endCharIndices: List<Int>,
    @Relationship(type = "OF_TYPE", direction = Relationship.Direction.INCOMING)
    var type: TopicType,
    @Version
    val version: Long? = null
) {
    constructor(name: String, endCharIndices: List<Int>, type: String) : this(
        name,
        endCharIndices,
        TopicType(type)
    )
}