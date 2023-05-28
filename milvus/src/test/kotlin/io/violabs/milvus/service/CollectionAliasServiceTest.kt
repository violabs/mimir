package io.violabs.milvus.service

import com.violabs.wesly.Wesley
import io.milvus.grpc.DataType
import io.violabs.milvus.domain.Milvus
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class CollectionAliasServiceTest(
    @Autowired private val collectionAliasService: CollectionAliasService,
    @Autowired private val collectionService: CollectionService,
    @Autowired private val dbSetupService: DbSetupService
) : Wesley() {

    private val collectionName = "books"
    private val aliasName = "publications"

    private val alias = Milvus.Collection.Alias(aliasName, collectionName)

    @BeforeEach
    fun setup() {
        collectionAliasService.drop(aliasName)
        collectionService.drop(collectionName)
        dbSetupService.createBookCollection()
    }

    @AfterEach
    fun teardown() {
        collectionAliasService.drop(aliasName)
        collectionService.drop(collectionName)
    }

    @Test
    fun `create will make an alias`() = test {
        expect {
            "Success"
        }

        whenever {
            collectionAliasService.create(alias)
        }
    }

    @Test
    fun `reassign will move the alias to the correct collection`() = test {
        val secondCollection = "paintings"

        setup {
            collectionService.create(
                Milvus.Collection(
                    secondCollection,
                    "Test",
                    shardsNumber = 2,
                    Milvus.FieldType("test", DataType.Int64, hasPrimaryKey = true, isAutoID = true)
                )
            )

            collectionAliasService.create(Milvus.Collection.Alias(aliasName, secondCollection))
        }

        expect { "Success" }

        whenever {
            collectionAliasService.reassign(alias)
        }
    }
}