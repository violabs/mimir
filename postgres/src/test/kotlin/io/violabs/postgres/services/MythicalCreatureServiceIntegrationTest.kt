package io.violabs.postgres.services

import io.violabs.core.SimpleTestHarness
import io.violabs.postgres.domain.MythicalCreature
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.util.UUID

@SpringBootTest
class MythicalCreatureServiceIntegrationTest(
    @Autowired private val repo: MythicalCreatureRepository,
    @Autowired private val service: MythicalCreatureService
) : SimpleTestHarness() {

    @BeforeEach
    fun reset() {
        repo.deleteAll()
    }

    @Test
    fun save() = test {
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