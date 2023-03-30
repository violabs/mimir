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
    fun createCollection(createRequest: Milvus.Collection.CreateRequest): String? =
        createRequest
            .toLibraryClass()
            .let(milvusClient::createCollection)
            ?.data
            ?.msg

    fun collectionExists(collectionName: String): Boolean =
        HasCollectionParam
            .newBuilder()
            .withCollectionName(collectionName)
            .build()
            .let(milvusClient::hasCollection)
            .data

    fun deleteCollection(collectionName: String): String? =
        DropCollectionParam
            .newBuilder()
            .withCollectionName(collectionName)
            .build()
            ?.let(milvusClient::dropCollection)
            ?.data
            ?.msg

    fun collectionDetails(collectionName: String): DescribeCollectionResponse =
        DescribeCollectionParam
            .newBuilder()
            .withCollectionName(collectionName)
            .build()
            .let { milvusClient.describeCollection(it) }
            .data

    fun collectionStatistics(collectionName: String): GetCollectionStatisticsResponse =
        GetCollectionStatisticsParam
            .newBuilder()
            .withCollectionName(collectionName)
            .build()
            .let(milvusClient::getCollectionStatistics)
            .data

    fun listCollections(): ShowCollectionsResponse =
        ShowCollectionsParam
            .newBuilder()
            .build()
            .let(milvusClient::showCollections)
            .data
}