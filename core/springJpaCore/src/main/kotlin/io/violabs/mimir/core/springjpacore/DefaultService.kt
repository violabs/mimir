package io.violabs.mimir.core.springjpacore

import org.springframework.data.jpa.repository.JpaRepository

abstract class DefaultService<T, ID, REPO : JpaRepository<T, ID>>(private val repository: REPO) {
    fun findById(id: ID): T? = repository.findById(id!!).orElse(null)
    fun findAll(): List<T> = repository.findAll().toList()
    fun <S : T> save(item: S): S = repository.save(item!!)
    fun <S : T> save(vararg items: S): List<S> = save(items.toList())
    fun <S : T> save(items: List<S>): List<S> = repository.saveAll(items).toList()
    fun existsById(id: ID): Boolean = repository.existsById(id!!)
    fun deleteById(id: ID): Boolean {
        repository.deleteById(id!!)

        return !existsById(id)
    }
}