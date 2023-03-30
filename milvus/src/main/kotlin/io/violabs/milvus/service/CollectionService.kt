package io.violabs.milvus.service

import io.milvus.client.MilvusServiceClient
import io.milvus.grpc.DescribeCollectionResponse
import io.milvus.grpc.GetCollectionStatisticsResponse
import io.milvus.grpc.ShowCollectionsResponse
import io.milvus.param.collection.*
import io.violabs.milvus.domain.Milvus
import org.springframework.stereotype.Service

@Service
class CollectionService(private val milvusClient: MilvusServiceClient) {
    fun create(createRequest: Milvus.Collection): String? =
        createRequest
            .toLibraryClass()
            .let(milvusClient::createCollection)
            ?.data
            ?.msg

    fun existsByName(collectionName: String): Boolean =
        HasCollectionParam
            .newBuilder()
            .withCollectionName(collectionName)
            .build()
            .let(milvusClient::hasCollection)
            .data

    fun drop(collectionName: String): String? =
        DropCollectionParam
            .newBuilder()
            .withCollectionName(collectionName)
            .build()
            ?.let(milvusClient::dropCollection)
            ?.data
            ?.msg

    fun findDetailsFor(collectionName: String): DescribeCollectionResponse =
        DescribeCollectionParam
            .newBuilder()
            .withCollectionName(collectionName)
            .build()
            .let { milvusClient.describeCollection(it) }
            .data

    fun findStatisticsFor(collectionName: String): GetCollectionStatisticsResponse =
        GetCollectionStatisticsParam
            .newBuilder()
            .withCollectionName(collectionName)
            .build()
            .let(milvusClient::getCollectionStatistics)
            .data

    fun list(): ShowCollectionsResponse =
        ShowCollectionsParam
            .newBuilder()
            .build()
            .let(milvusClient::showCollections)
            .data
}