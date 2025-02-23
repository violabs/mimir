package io.violabs.mimir.graphql.async.domain

import io.violabs.mimir.graphql.async.config.JsonConverter
import io.violabs.mimir.graphql.async.domain.input.CreateMythicalCreatureInput
import jakarta.persistence.*
import java.util.UUID

@Entity
@Table(name = "mythical_creature")
class MythicalCreature(
    val name: String? = null,
    val numberOfLegs: Int = 0,
    val hasHair: Boolean = false,
    @Convert(converter = JsonConverter::class)
    @Column(columnDefinition = "jsonb")
    val taxonomyTags: List<String>? = null,
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: UUID? = null
) {
    constructor(request: CreateMythicalCreatureInput) : this(
        request.name,
        request.numberOfLegs,
        request.hasHair,
        request.taxonomyTags
    )
}