package io.violabs.milvus.service

import io.milvus.client.MilvusServiceClient
import io.milvus.param.alias.DropAliasParam
import io.violabs.milvus.domain.Milvus
import org.springframework.stereotype.Service

@Service
class CollectionAliasService(private val milvusClient: MilvusServiceClient) {
    fun create(alias: Milvus.Collection.Alias): String? =
        milvusClient
            .createAlias(alias.toCreateLibraryClass())
            ?.data
            ?.msg

    fun drop(alias: String): String? =
        DropAliasParam
            .newBuilder()
            .withAlias(alias)
            .build()
            .let(milvusClient::dropAlias)
            ?.data
            ?.msg

    fun reassign(alias: Milvus.Collection.Alias): String? =
        milvusClient
            .alterAlias(alias.toAlterLibraryClass())
            ?.data
            ?.msg
}