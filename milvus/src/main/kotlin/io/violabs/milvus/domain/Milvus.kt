package io.violabs.milvus.domain

import io.milvus.grpc.DataType
import io.milvus.param.alias.AlterAliasParam
import io.milvus.param.alias.CreateAliasParam
import io.milvus.param.collection.CreateCollectionParam
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
}