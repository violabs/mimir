package io.violabs.mimir.graphql.async.domain.input

data class CreateMythicalCreatureInput(
    val name: String,
    val numberOfLegs: Int = 0,
    val hasHair: Boolean = false,
    val taxonomyTags: List<String>? = null
)