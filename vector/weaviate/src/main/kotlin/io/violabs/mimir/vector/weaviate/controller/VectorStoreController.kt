package io.violabs.mimir.vector.weaviate.controller

import io.violabs.mimir.vector.weaviate.service.DataService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("vector-store")
class VectorStoreController(private val dataService: DataService) {

    @PostMapping("add")
    fun addContent(@RequestBody addRequest: AddRequest) {
        dataService.addContent(addRequest.sentences)
    }

    @GetMapping("search")
    fun search(@RequestParam query: String) = dataService.search(query)

    data class AddRequest(val sentences: List<String>)
}