package io.violabs.mimir.database.postgres.services

import io.violabs.mimir.core.springJpaCore.MythicalCreatureRepository
import io.violabs.mimir.core.springJpaCore.MythicalCreatureService
import io.violabs.mimir.core.springJpaCore.ServiceIntegrationTestHarness
import io.violabs.mimir.core.springJpaCore.domain.MythicalCreature
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.util.UUID

@SpringBootTest
class MythicalCreatureServiceIntegrationTest(
    @Autowired private val mythicalCreatureRepository: MythicalCreatureRepository,
    @Autowired private val mythicalCreatureService: MythicalCreatureService
) : ServiceIntegrationTestHarness<UUID, MythicalCreature, MythicalCreatureRepository, MythicalCreatureService>(
    mythicalCreatureRepository,
    mythicalCreatureService,
    Options(
        saveEntity = MythicalCreature(name = "Chimera"),
        saveCheckProperties = mapOf("name" to "Chimera"),
        findByIdEntity = MythicalCreature(name = "Gorgon"),
        findAllEntities = listOf(
            MythicalCreature(name = "Werewolf"),
            MythicalCreature(name = "Unicorn")
        ),
        sortFn = { it.sortedBy(MythicalCreature::name) },
        deleteByIdEntity = MythicalCreature(name = "Oni")
    )
) {
    @BeforeEach
    override fun setup() = reset()

    @Test
    override fun testSave() = save()

    @Test
    override fun testFindById() = findById()

    @Test
    override fun testFindAll() = findAll()

    @Test
    override fun testDeleteById() = delete()
}