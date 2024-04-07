package io.violabs.milvus.service

import io.milvus.grpc.DataType
import io.violabs.milvus.domain.Milvus
import mu.KLogging
import org.springframework.stereotype.Service

@Service
class DbSetupService(private val collectionService: CollectionService) {
    fun createBookCollection(): String? {
        val request = Milvus.Collection(
            collectionName = "books",
            description = "Test book search",
            shardsNumber = 2,
            Milvus.FieldType(
                name = "book_id",
                dataType = DataType.Int64,
                hasPrimaryKey = true,
                isAutoID = false
            ),
            Milvus.FieldType(
                name = "word_count",
                dataType = DataType.Int64,
            ),
            Milvus.FieldType(
                name = "book_intro",
                dataType = DataType.FloatVector,
                dimensions = 2
            )
        )

        logger.info { "Creating collection: $request" }

        return collectionService.create(request)
    }

    companion object : KLogging()
}