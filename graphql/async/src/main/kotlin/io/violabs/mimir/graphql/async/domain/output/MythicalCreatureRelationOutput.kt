package io.violabs.mimir.graphql.async.domain.output

import io.violabs.mimir.graphql.async.domain.MythicalCreature

data class MythicalCreatureRelationOutput(
    val from: MythicalCreature,
    val to: MythicalCreature,
    val description: String? = null
)