package io.violabs.milvus.service

import io.milvus.client.MilvusServiceClient
import io.milvus.grpc.ShowPartitionsResponse
import io.milvus.param.partition.LoadPartitionsParam
import io.milvus.param.partition.ReleasePartitionsParam
import io.milvus.param.partition.ShowPartitionsParam
import io.violabs.milvus.domain.Milvus
import org.springframework.stereotype.Service

@Service
class PartitionService(private val milvusClient: MilvusServiceClient) {
    
    fun create(partition: Milvus.Partition): String? =
        milvusClient
            .createPartition(partition.toCreateLibraryClass())
            ?.data
            ?.msg

    fun drop(partition: Milvus.Partition): String? =
        milvusClient
            .dropPartition(partition.toDropLibraryClass())
            ?.data
            ?.msg

    fun existsByCollectionAndName(partition: Milvus.Partition): Boolean =
        milvusClient.hasPartition(partition.toHasLibraryClass())?.data == true

    fun list(collectionName: String): ShowPartitionsResponse? =
        ShowPartitionsParam
            .newBuilder()
            .withCollectionName(collectionName)
            .build()
            .let(milvusClient::showPartitions)
            ?.data

    fun <T> useMemory(collectionName: String, vararg partitionNames: String, runnable: () -> T?): T? {
        loadInMemory(collectionName, *partitionNames)

        return runnable().also { releaseFromMemory(collectionName, *partitionNames) }
    }

    fun loadInMemory(collectionName: String, vararg partitionNames: String): String? =
        LoadPartitionsParam
            .newBuilder()
            .withCollectionName(collectionName)
            .withPartitionNames(partitionNames.toList())
            .build()
            .let(milvusClient::loadPartitions)
            ?.data
            ?.msg

    fun releaseFromMemory(collectionName: String, vararg partitionNames: String): String? =
        ReleasePartitionsParam
            .newBuilder()
            .withCollectionName(collectionName)
            .withPartitionNames(partitionNames.toList())
            .build()
            .let(milvusClient::releasePartitions)
            ?.data
            ?.msg
}