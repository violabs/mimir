package io.violabs.mimir.core.springJpaCore

interface DbEntity<ID> {
    var id: ID?
    fun toPropertyMap(): PropertyMap
}