package io.violabs.sharedsql.test

import io.violabs.core.SimpleTestHarness
import io.violabs.sharedsql.domain.MythicalCreature
import io.violabs.sharedsql.services.MythicalCreatureRepository
import io.violabs.sharedsql.services.MythicalCreatureService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.UUID

abstract class MythicalCreatureServiceIntegrationTestSetup(
    private val repo: MythicalCreatureRepository,
    private val service: MythicalCreatureService
) : SimpleTestHarness() {

    @BeforeEach
    fun reset() {
        repo.deleteAll()
    }

    @Test
    fun save() = test {
        println("HELLO")
        expect { SaveTestObject(hasId = true, "Chimera") }

        whenever {
            val creature = MythicalCreature(name = "Chimera")

            service
                .save(creature)
                .let(::SaveTestObject)
        }
    }

    @Test
    fun findById() = test {
        val expected: MythicalCreature = repo.save(MythicalCreature(name = "Gorgon"))

        expect { expected }

        whenever { service.findById(expected.id!!) }
    }

    @Test
    fun findAll() = test {
        expect {
            repo.saveAll(
                listOf(
                    MythicalCreature(name = "Werewolf"),
                    MythicalCreature(name = "Unicorn")
                )
            )
        }

        whenever { service.findAll() }
    }

    @Test
    fun delete() = test {
        expect { true }

        whenever {
            val id: UUID = repo.save(MythicalCreature(name = "Oni")).id!!

            service.deleteById(id)
        }
    }
}

data class SaveTestObject(
    val hasId: Boolean,
    val name: String
) {
    constructor(mythicalCreature: MythicalCreature) : this(
        mythicalCreature.id != null,
        mythicalCreature.name!!
    )
}