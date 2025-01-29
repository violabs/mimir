package io.violabs.mimir.vector.weaviate.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import io.violabs.mimir.vector.weaviate.service.DataService
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/vector-store")
@Tag(name = "vector-operations", description = "Endpoints for vector store operations")
class VectorStoreController(private val dataService: DataService) {

    @Operation(
        summary = "Add content to vector store",
        description = """
            Adds a list of sentences to the vector store. Each sentence will be:
            1. Converted to an embedding using Ollama
            2. Stored in Weaviate with its vector representation
            3. Available for similarity search
        """
    )
    @ApiResponses(value = [
        ApiResponse(
            responseCode = "200",
            description = "Content successfully added"
        ),
        ApiResponse(
            responseCode = "400",
            description = "Invalid request format or empty content list",
            content = [Content(mediaType = MediaType.APPLICATION_JSON_VALUE)]
        ),
        ApiResponse(
            responseCode = "500",
            description = "Error occurred while processing the request",
            content = [Content(mediaType = MediaType.APPLICATION_JSON_VALUE)]
        )
    ])
    @PostMapping("add")
    fun addContent(
        @RequestBody
        @Schema(
            description = "List of sentences to add to the vector store",
            required = true,
            example = """{"sentences": ["First sentence to store", "Second sentence to store"]}"""
        )
        addRequest: AddRequest
    ) {
        dataService.addContent(addRequest.sentences)
    }

    @Operation(
        summary = "Search vector store",
        description = """
            Performs a semantic search in the vector store using the provided query. The process:
            1. Converts query to embedding using Ollama
            2. Searches Weaviate for similar vectors
            3. Returns matching content ordered by similarity
        """
    )
    @ApiResponses(value = [
        ApiResponse(
            responseCode = "200",
            description = "Search completed successfully",
            content = [Content(mediaType = MediaType.APPLICATION_JSON_VALUE)]
        ),
        ApiResponse(
            responseCode = "400",
            description = "Invalid or empty query",
            content = [Content(mediaType = MediaType.APPLICATION_JSON_VALUE)]
        ),
        ApiResponse(
            responseCode = "500",
            description = "Error occurred while processing the search",
            content = [Content(mediaType = MediaType.APPLICATION_JSON_VALUE)]
        )
    ])
    @GetMapping("search")
    fun search(
        @Parameter(
            description = "Query text to search for similar content",
            required = true,
            example = "example search query"
        )
        @RequestParam query: String
    ) = dataService.search(query)

    @Schema(description = "Request object for adding content to the vector store")
    data class AddRequest(
        @field:Schema(
            description = "List of sentences to store",
            required = true,
            example = """["First sentence", "Second sentence"]"""
        )
        val sentences: List<String>
    )
}