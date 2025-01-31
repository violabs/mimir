package io.violabs.mimir.core.springjpacore

interface DbEntity<ID> {
    var id: ID?
    fun toPropertyMap(): PropertyMap
}