package io.violabs.milvus.service

import io.milvus.client.MilvusServiceClient
import io.milvus.grpc.MutationResult
import io.violabs.milvus.domain.Milvus
import org.springframework.stereotype.Service

@Service
class BookService(private val milvusClient: MilvusServiceClient) {

    fun save(insert: Milvus.InsertRequest): MutationResult? {
        val insertParam = insert.toLibraryClass()

        return milvusClient.insert(insertParam)?.data
    }
}