package io.violabs.milvus.service

import com.violabs.wesly.Wesley
import com.violabs.wesly.expectTrue
import io.violabs.milvus.domain.Milvus
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class PartitionServiceTest(
    @Autowired private val dbSetupService: DbSetupService,
    @Autowired private val collectionService: CollectionService,
    @Autowired private val partitionService: PartitionService
) : Wesley() {

    private val collectionName = "books"
    private val partitionName = "novel"

    private val partition = Milvus.Partition(collectionName, partitionName)

    @Test
    fun `test full service will make a partition`() = test {
        setup {
            collectionService.drop(collectionName)
            dbSetupService.createBookCollection()
        }

        expectTrue()

        whenever {
            val created = partitionService.create(partition)

            val exists = partitionService.existsByCollectionAndName(partition)

            val listed = partitionService.useMemory(collectionName, partitionName) {
                partitionService.list(collectionName)
            }

            listed?.partitionNamesList

            val dropped = partitionService.drop(partition)

            val notExists = !partitionService.existsByCollectionAndName(partition)

            sequenceOf(created, dropped).all("Success"::equals)
                    && exists
                    && notExists
                    && listed!!.partitionNamesList!! == listOf("_default", partitionName)
        }

        teardown {
            collectionService.drop(collectionName)
        }
    }
}