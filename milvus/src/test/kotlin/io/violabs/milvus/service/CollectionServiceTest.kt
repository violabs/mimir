package io.violabs.milvus.service

import com.violabs.wesly.Wesley
import com.violabs.wesly.expectTrue
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class CollectionServiceTest(
    @Autowired private val collectionService: CollectionService,
    @Autowired private val dbSetupService: DbSetupService
) : Wesley() {
    private val collectionName = "books"

    @BeforeEach
    fun setup() {
        collectionService.drop(collectionName)
        dbSetupService.createBookCollection()
    }

    @AfterEach
    fun teardown() {
        collectionService.drop(collectionName)
    }

    @Test
    fun `collectionDetails should get the details of a collection`() = test {
        expectTrue()

        whenever {
            val result = collectionService.findDetailsFor(collectionName)

            result.schema.name == collectionName
        }
    }

    @Test
    fun `collectionStatistics should get the statistics of a collection`() = test {
        expectTrue()

        whenever {
            val result = collectionService.findStatisticsFor(collectionName)

            println(result)

            result.statsList[0].value == "0"
        }
    }

    @Test
    fun `listCollections should list all collections`() = test {
        expectTrue()

        whenever {
            val result = collectionService.list()

            println(result)

            result.collectionNamesList[0] == collectionName
        }
    }
}