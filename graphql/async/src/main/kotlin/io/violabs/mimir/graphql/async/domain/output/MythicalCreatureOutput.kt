package io.violabs.mimir.graphql.async.domain.output

import java.util.UUID

data class MythicalCreatureOutput(
    val id: UUID,
    val name: String,
    val numberOfLegs: Int = 0,
    val hasHair: Boolean = false,
    val taxonomyTags: List<String>? = null,
    val relations: List<MythicalCreatureRelationOutput>? = null
)