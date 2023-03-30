package io.violabs.milvus.service

import com.fasterxml.jackson.databind.ObjectMapper
import io.milvus.client.MilvusServiceClient
import io.violabs.milvus.domain.Milvus
import org.springframework.stereotype.Service

@Service
class IndexService(
    private val milvusClient: MilvusServiceClient,
    private val objectMapper: ObjectMapper
) {
    fun create(index: Milvus.Index): String? =
        milvusClient
            .createIndex(index.toCreateLibraryClass(objectMapper))
            ?.data
            ?.msg

    fun drop(index: Milvus.Index): String? =
        milvusClient
            .dropIndex(index.toDropLibraryClass().also { println(it.fieldName) })
            ?.also { println(it) }
            ?.data
            ?.msg
}