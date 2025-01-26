package io.violabs.springjpacore

import io.violabs.mimir.core.SimpleTestHarness
import org.springframework.data.jpa.repository.JpaRepository

abstract class ServiceIntegrationTestHarness<
        ID,
        T : DbEntity<ID>,
        REPO : JpaRepository<T, ID>,
        SRVC : DefaultService<T, ID, REPO>
>(
    private val repo: REPO,
    private val service: SRVC,
    private val options: Options<ID, T>
) : SimpleTestHarness() {

    abstract fun setup()
    abstract fun testSave()
    abstract fun testFindById()
    abstract fun testFindAll()
    abstract fun testDeleteById()

    fun reset() {
        repo.deleteAll()
    }

    fun save() = test {
        println("HELLO")
        expect {
            SaveTestObject<ID, T>(hasId = true, options.saveCheckProperties)
        }

        whenever {
            service
                .save(options.saveEntity)
                .let { SaveTestObject(it, options) }
        }
    }

    fun findById() = test {
        val expected: T = repo.save(options.findByIdEntity)

        expect { expected }

        whenever { service.findById(expected.id!!) }
    }

    fun findAll() = test {
        expect {
            options.sortFn(repo.saveAll(options.findAllEntities))
        }

        whenever {
            options.sortFn(service.findAll())
        }
    }

    fun delete() = test {
        expect { true }

        whenever {
            val id: ID = repo.save(options.deleteByIdEntity).id!!

            service.deleteById(id)
        }
    }

    class Options<ID, T : DbEntity<ID>>(
        val saveEntity: T,
        val saveCheckProperties: PropertyMap = mapOf(),
        val findByIdEntity: T,
        val findAllEntities: List<T>,
        val sortFn: (List<T>) -> List<T>,
        val deleteByIdEntity: T
    )
}

data class SaveTestObject<ID, out T : DbEntity<ID>>(
    val hasId: Boolean,
    var properties: PropertyMap = mapOf()
) {
    constructor(entity: T, options: ServiceIntegrationTestHarness.Options<*, *>) : this(entity.id != null) {
        val propertyNames: Set<PropertyName> = options.saveCheckProperties.keys

        properties =
            entity
                .toPropertyMap()
                .filterKeys(propertyNames::contains)
    }
}
