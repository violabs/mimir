package io.violabs.mimir.graphql.async.domain

data class MythicalCreatureRelation(
    val targetMythicalCreature: MythicalCreature,
    val sourceMythicalCreature: MythicalCreature,
    val description: String? = null
)