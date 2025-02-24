package io.violabs.mimir.graphql.async.service

import io.violabs.mimir.graphql.async.domain.*
import io.violabs.mimir.graphql.async.domain.input.CreateMythicalCreatureInput
import io.violabs.mimir.graphql.async.domain.input.UpdateMythicalCreatureInput
import io.violabs.mimir.graphql.async.domain.output.UpdateMythicalCreatureOutput
import io.violabs.mimir.graphql.async.repository.MythicalCreatureRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Service
import java.util.*

@Service
class MythicalCreatureService(private val mythicalCreatureRepository: MythicalCreatureRepository) {
    suspend fun getMythicalCreatureById(id: UUID): MythicalCreature? = withContext(Dispatchers.IO) {
        mythicalCreatureRepository.findById(id).orElse(null)
    }

    suspend fun searchMythicalCreatures(query: String): List<MythicalCreature> = withContext(Dispatchers.IO) {
       listOf()
    }

    suspend fun createMythicalCreature(request: CreateMythicalCreatureInput): UUID = withContext(Dispatchers.IO) {
        val saved = mythicalCreatureRepository.save(MythicalCreature(request))

        saved.id ?: throw UninitializedPropertyAccessException()
    }

    suspend fun updateMythicalCreature(request: UpdateMythicalCreatureInput): UpdateMythicalCreatureOutput = withContext(Dispatchers.IO) {
        UpdateMythicalCreatureOutput()
    }

    suspend fun getRelationsFrom(animalId: UUID?): List<MythicalCreatureRelation> = withContext(Dispatchers.IO) {
        listOf()
    }

    suspend fun getRelationsTo(animalId: UUID?): List<MythicalCreatureRelation> = withContext(Dispatchers.IO) {
        listOf()
    }
}