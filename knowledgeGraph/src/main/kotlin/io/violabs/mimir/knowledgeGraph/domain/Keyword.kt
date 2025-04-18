package io.violabs.mimir.knowledgeGraph.domain

import org.neo4j.ogm.annotation.Id
import org.neo4j.ogm.annotation.NodeEntity

@NodeEntity
data class Keyword(
    @Id
    val name: String? = null,
    val id: Long? = null
)