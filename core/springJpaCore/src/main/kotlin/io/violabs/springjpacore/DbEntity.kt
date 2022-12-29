package io.violabs.springjpacore

interface DbEntity<ID> {
    var id: ID?
    fun toPropertyMap(): PropertyMap
}