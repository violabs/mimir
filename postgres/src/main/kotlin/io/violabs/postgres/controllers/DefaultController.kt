package io.violabs.postgres.controllers

import io.violabs.postgres.services.DefaultService
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

abstract class DefaultController<T, ID, S : DefaultService<T, ID, *>>(private val service: S) {

    @GetMapping
    fun findAll(): List<T> = service.findAll()

    @GetMapping("{id}")
    fun findById(id: ID): T? = service.findById(id)

    @PostMapping
    fun save(@RequestBody item: T): T = service.save(item)

    @DeleteMapping
    fun deleteById(id: ID): Boolean = service.deleteById(id)
}