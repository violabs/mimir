package io.violabs.milvus.service

import com.violabs.wesly.Wesley
import com.violabs.wesly.expectTrue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class DbSetupServiceTest(
    @Autowired private val dbSetupService: DbSetupService,
    @Autowired private val collectionService: CollectionService
) : Wesley() {
    private val collectionName = "books"

    @Test
    fun `createBookCollection should create the correct collection`() = test {
        setup {
            collectionService.deleteCollection(collectionName)
        }

        expectTrue()

        whenever {
            val result = dbSetupService.createBookCollection()

            result == "Success" && collectionService.collectionExists(collectionName)
        }

        teardown {
            collectionService.deleteCollection(collectionName)
        }
    }
}
