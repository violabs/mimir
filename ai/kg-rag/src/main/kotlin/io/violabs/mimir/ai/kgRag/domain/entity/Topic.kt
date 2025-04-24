package io.violabs.mimir.ai.kgRag.domain.entity

import org.springframework.data.neo4j.core.schema.Id
import org.springframework.data.neo4j.core.schema.Node
import org.springframework.data.neo4j.core.schema.Relationship

@Node
data class Topic(
    @Id
    val name: String,
    @Relationship(type = "OF_TYPE", direction = Relationship.Direction.INCOMING)
    var type: TopicType
) {
    constructor(name: String, type: String) : this(
        name,
        TopicType(type)
    )
}