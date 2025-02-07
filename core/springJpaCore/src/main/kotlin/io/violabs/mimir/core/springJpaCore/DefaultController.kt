package io.violabs.mimir.core.springJpaCore

import org.springframework.web.bind.annotation.*

abstract class DefaultController<T, ID, S : DefaultService<T, ID, *>>(private val service: S) {

    @GetMapping
    fun findAll(): List<T> = service.findAll()

    @GetMapping("{id}")
    fun findById(@PathVariable id: ID): T? = service.findById(id)

    @PostMapping
    fun save(@RequestBody item: T): T = service.save(item)

    @PostMapping("/all")
    fun saveAll(@RequestBody request: SaveListRequest<T>): List<T> = service.save(request.items)

    @GetMapping("{id}/exists")
    fun existsById(@PathVariable id: ID): Boolean = service.existsById(id)

    @DeleteMapping
    fun deleteById(id: ID): Boolean = service.deleteById(id)

    @JvmInline
    value class SaveListRequest<T>(val items: List<T>)
}