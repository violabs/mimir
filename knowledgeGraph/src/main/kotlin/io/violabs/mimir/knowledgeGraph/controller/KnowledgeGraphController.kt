package io.violabs.mimir.knowledgeGraph.controller

import io.violabs.mimir.knowledgeGraph.domain.Quote
import io.violabs.mimir.knowledgeGraph.service.KnowledgeGraphService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api")
class KnowledgeGraphController(private val service: KnowledgeGraphService) {

    @PostMapping("/quotes")
    fun addQuote(@RequestBody request: QuoteRequest) {
        service.addQuote(request.text, request.keywords)
    }

    @GetMapping("/quotes/search")
    fun searchQuotes(@RequestParam keyword: String): List<Quote> {
        return service.findQuotesByKeyword(keyword)
    }
}

data class QuoteRequest(val text: String, val keywords: List<String>)