package io.violabs.milvus.domain

import com.fasterxml.jackson.databind.ObjectMapper
import io.milvus.grpc.DataType
import io.milvus.param.IndexType
import io.milvus.param.MetricType
import io.milvus.param.alias.AlterAliasParam
import io.milvus.param.alias.CreateAliasParam
import io.milvus.param.collection.CreateCollectionParam
import io.milvus.param.dml.InsertParam
import io.milvus.param.index.CreateIndexParam
import io.milvus.param.index.DropIndexParam
import io.milvus.param.partition.CreatePartitionParam
import io.milvus.param.partition.DropPartitionParam
import io.milvus.param.partition.HasPartitionParam
import io.milvus.param.collection.FieldType as MilvusFieldType

//https://milvus.io/docs/v2.0.x/create_collection.md
object Milvus {
    data class FieldType(
        val name: String? = null,
        val dataType: DataType? = null,
        val hasPrimaryKey: Boolean? = null,
        val isAutoID: Boolean? = null,
        val dimensions: Int? = null
    ) {
        fun toLibraryClass(): MilvusFieldType =
            MilvusFieldType
                .newBuilder()
                .also {
                    name?.let(it::withName)
                    dataType?.let(it::withDataType)
                    hasPrimaryKey?.let(it::withPrimaryKey)
                    isAutoID?.let(it::withAutoID)
                    dimensions?.let(it::withDimension)
                }
                .build()
    }

    data class Collection(
        val collectionName: String? = null,
        val description: String? = null,
        val shardsNumber: Int? = null,
        val fieldTypes: List<FieldType>? = null
    ) {
        constructor(
            collectionName: String? = null,
            description: String? = null,
            shardsNumber: Int? = null,
            vararg list: FieldType
        ) : this(collectionName, description, shardsNumber, list.toList())

        fun toLibraryClass(): CreateCollectionParam =
            CreateCollectionParam
                .newBuilder()
                .also {
                    collectionName?.let(it::withCollectionName)
                    description?.let(it::withDescription)
                    shardsNumber?.let(it::withShardsNum)
                    fieldTypes?.forEach { fieldType ->
                        it.addFieldType(fieldType.toLibraryClass())
                    }
                }
                .build()

        data class Alias(val name: String, val collectionName: String) {
            fun toCreateLibraryClass(): CreateAliasParam =
                CreateAliasParam
                    .newBuilder()
                    .withAlias(name)
                    .withCollectionName(collectionName)
                    .build()

            fun toAlterLibraryClass(): AlterAliasParam =
                AlterAliasParam
                    .newBuilder()
                    .withAlias(name)
                    .withCollectionName(collectionName)
                    .build()
        }
    }

    data class Partition(
        val collectionName: String? = null,
        val name: String? = null
    ) {
        fun toCreateLibraryClass(): CreatePartitionParam =
            CreatePartitionParam
                .newBuilder()
                .also {
                    collectionName?.let(it::withCollectionName)
                    name?.let(it::withPartitionName)
                }
                .build()

        fun toDropLibraryClass(): DropPartitionParam =
            DropPartitionParam
                .newBuilder()
                .also {
                    collectionName?.let(it::withCollectionName)
                    name?.let(it::withPartitionName)
                }
                .build()

        fun toHasLibraryClass(): HasPartitionParam =
            HasPartitionParam
                .newBuilder()
                .also {
                    collectionName?.let(it::withCollectionName)
                    name?.let(it::withPartitionName)
                }
                .build()
    }

    data class Index(
        val collectionName: String? = null,
        val fieldName: String? = null,
        val indexType: IndexType? = null,
        val metricType: MetricType? = null,
        val extraParam: Map<String, Any?>? = null,
        val syncMode: Boolean? = null
    ) {
        fun toCreateLibraryClass(objectMapper: ObjectMapper): CreateIndexParam =
            CreateIndexParam
                .newBuilder()
                .also {
                    collectionName?.let(it::withCollectionName)
                    fieldName?.let(it::withFieldName)
                    indexType?.let(it::withIndexType)
                    metricType?.let(it::withMetricType)
                    extraParam?.let { map ->
                        it.withExtraParam(objectMapper.writeValueAsString(map))
                    }
                    syncMode?.let(it::withSyncMode)
                }
                .build()

        fun toDropLibraryClass(): DropIndexParam =
            DropIndexParam
                .newBuilder()
                .also {
                    collectionName?.let(it::withCollectionName)
                    fieldName?.let(it::withIndexName)
                }
                .build()
    }

    data class InsertRequest(
        val collectionName: String? = null,
        val partitionName: String? = null,
        val fields: List<Field>? = null
    ) {
        constructor(
            collectionName: String? = null,
            partitionName: String? = null,
            vararg list: Field
        ) : this(collectionName, partitionName, list.toList())

        data class Field(
            val name: String? = null,
            val dataType: DataType? = null,
            val values: List<Any>? = null
        ) {
            fun toLibraryClass(): InsertParam.Field = InsertParam.Field(name, values)
        }

        fun toLibraryClass(): InsertParam =
            InsertParam
                .newBuilder()
                .also {
                    collectionName?.let(it::withCollectionName)
                    partitionName?.let(it::withPartitionName)
                    fields
                        ?.map { field -> field.toLibraryClass() }
                        ?.also(it::withFields)
                }
                .build()
    }
}