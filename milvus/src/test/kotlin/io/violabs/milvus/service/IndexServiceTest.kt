package io.violabs.milvus.service

import com.violabs.wesly.Wesley
import com.violabs.wesly.expectTrue
import io.milvus.param.IndexType
import io.milvus.param.MetricType
import io.violabs.milvus.domain.Milvus
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class IndexServiceTest(
    @Autowired private val indexService: IndexService,
    @Autowired private val dbSetupService: DbSetupService,
    @Autowired private val collectionService: CollectionService
) : Wesley() {

    private val collectionName = "books"

    val index = Milvus.Index(
        collectionName = collectionName,
        fieldName = "book_intro",
        indexType = IndexType.IVF_FLAT,
        metricType = MetricType.L2,
        extraParam = mapOf("nlist" to 1024),
        syncMode = false
    )

    @Test
    fun `index flow check`() = test {
        setup {
            collectionService.drop(collectionName)
            dbSetupService.createBookCollection()
        }

        expectTrue()

        whenever {
            val created = indexService.create(index)
            val dropped = indexService.drop(index)

            sequenceOf(created, dropped).all("Success"::equals)
        }

        teardown {
            collectionService.drop(collectionName)
        }
    }
}