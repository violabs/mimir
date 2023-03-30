package io.violabs.milvus.service

import com.violabs.wesly.Wesley
import com.violabs.wesly.expectTrue
import io.milvus.grpc.DataType
import io.violabs.milvus.domain.Milvus
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.util.Random

@SpringBootTest
class BookServiceTest(
    @Autowired private val bookService: BookService,
    @Autowired private val dbSetupService: DbSetupService,
    @Autowired private val collectionService: CollectionService,
    @Autowired private val partitionService: PartitionService
) : Wesley() {

    private val collectionName = "books"

    private val partition = Milvus.Partition(
        collectionName = collectionName,
        name = "novels"
    )

    @Test
    fun `save will correctly add the item`() = test {
        setup {
            collectionService.drop(collectionName)
            partitionService.drop(partition)

            dbSetupService.createBookCollection()
            partitionService.create(partition)
        }

        expectTrue()

        whenever {
            val bookInsert = buildBookInsert()

            bookService.save(bookInsert) != null
        }

        teardown {
            collectionService.drop(collectionName)
            partitionService.drop(partition)
        }
    }

    private fun buildBookInsert(): Milvus.InsertRequest {
        val random = Random()

        val bookIdArray: MutableList<Long> = mutableListOf()
        val wordCountArray: MutableList<Long> = mutableListOf()
        val bookIntroArray: MutableList<List<Float>> = mutableListOf()

        for (i in 0L until 2000) {
            bookIdArray.add(i)
            wordCountArray.add(i + 10_000)

            val vector = (0 until 2).map { random.nextFloat() }

            bookIntroArray.add(vector)
        }

        return Milvus.InsertRequest(
            collectionName = collectionName,
            partitionName = "novels",
            fields = listOf(
                Milvus.InsertRequest.Field(
                    name = "book_id",
                    dataType = DataType.Int64,
                    values = bookIdArray
                ),
                Milvus.InsertRequest.Field(
                    name = "word_count",
                    dataType = DataType.Int64,
                    values = wordCountArray
                ),
                Milvus.InsertRequest.Field(
                    name = "book_intro",
                    dataType = DataType.FloatVector,
                    values = bookIntroArray
                )
            )
        )
    }
}