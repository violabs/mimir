package io.violabs.postgres.services

import io.violabs.postgres.domain.MythicalCreature
import io.violabs.springjpacore.DefaultService
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import org.springframework.stereotype.Service
import java.util.*

@Repository
interface MythicalCreatureRepository : JpaRepository<MythicalCreature, UUID> {
    @Query("SELECT creature FROM MythicalCreature creature WHERE creature.name = :name")
    fun findByName(name: String): List<MythicalCreature>
}

@Service
class MythicalCreatureService(repo: MythicalCreatureRepository) :
    DefaultService<MythicalCreature, UUID, MythicalCreatureRepository>(repo)