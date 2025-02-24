package io.violabs.mimir.graphql.async.resolver

import io.violabs.mimir.core.common.VLoggable
import io.violabs.mimir.graphql.async.domain.*
import io.violabs.mimir.graphql.async.domain.input.CreateMythicalCreatureInput
import io.violabs.mimir.graphql.async.domain.input.UpdateMythicalCreatureInput
import io.violabs.mimir.graphql.async.domain.output.UpdateMythicalCreatureOutput
import io.violabs.mimir.graphql.async.service.MythicalCreatureService
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.graphql.data.method.annotation.SchemaMapping
import org.springframework.stereotype.Controller
import java.util.UUID

@Controller
class MythicalCreatureResolver(
    private val mythicalCreatureService: MythicalCreatureService
) : VLoggable {
    @QueryMapping
    suspend fun getMythicalCreature(@Argument id: String): MythicalCreature? = trace("test") {
        log("Getting mythical creature $id")
        mythicalCreatureService.getMythicalCreatureById(UUID.fromString(id))
    }

    @QueryMapping
    suspend fun searchMythicalCreatures(@Argument query: String): List<MythicalCreature> =
        mythicalCreatureService.searchMythicalCreatures(query)

    @MutationMapping
    suspend fun createMythicalCreature(@Argument input: CreateMythicalCreatureInput): UUID =
        mythicalCreatureService.createMythicalCreature(input)

    @MutationMapping
    suspend fun updateMythicalCreature(
        @Argument input: UpdateMythicalCreatureInput
    ): UpdateMythicalCreatureOutput = mythicalCreatureService.updateMythicalCreature(input)

    @SchemaMapping(typeName = "MythicalCreature")
    suspend fun relations(entity: MythicalCreature): List<MythicalCreatureRelation> = coroutineScope {
        // Example of structured concurrency with parallel fetching
        val fromRelations = async { mythicalCreatureService.getRelationsFrom(entity.id) }
        val toRelations = async { mythicalCreatureService.getRelationsTo(entity.id) }
        
        fromRelations.await() + toRelations.await()
    }
}