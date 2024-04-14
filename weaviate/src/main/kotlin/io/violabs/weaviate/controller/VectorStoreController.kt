package io.violabs.weaviate.controller

import io.violabs.weaviate.service.DataService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("vector-store")
class VectorStoreController(private val dataService: DataService) {

    @PostMapping("add")
    fun addContent(@RequestBody addRequest: AddRequest) {
        dataService.addContent(addRequest.sentences)
    }

    @GetMapping("search")
    fun search(@RequestParam query: String, @RequestParam limit: Int) = dataService.search(query, limit)

    data class AddRequest(val sentences: List<String>)
}