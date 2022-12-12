package io.violabs.postgres.services

import org.springframework.data.repository.CrudRepository

abstract class DefaultService<T, ID, REPO : CrudRepository<T, ID>>(private val repository: REPO) {
    fun findById(id: ID): T? = repository.findById(id!!).orElse(null)
    fun findAll(): List<T> = repository.findAll().toList()
    fun <S : T> save(item: S): S = repository.save(item!!)
    private fun existsById(id: ID): Boolean = repository.existsById(id!!)
    fun deleteById(id: ID): Boolean {
        repository.deleteById(id!!)

        return !existsById(id)
    }
}