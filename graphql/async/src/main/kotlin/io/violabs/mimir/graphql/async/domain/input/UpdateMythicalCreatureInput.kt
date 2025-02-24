package io.violabs.mimir.graphql.async.domain.input

import java.util.UUID

data class UpdateMythicalCreatureInput(
    val id: UUID,
    val name: String? = null,
    val numberOfLegs: Int? = null,
    val hasHair: Boolean? = null,
    val taxonomyTags: List<String>? = null
)