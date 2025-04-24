package io.violabs.mimir.ai.kgRag.controller

import io.violabs.mimir.ai.kgRag.domain.DataIngestDocument
import io.violabs.mimir.ai.kgRag.domain.api.TitleRequest
import io.violabs.mimir.ai.kgRag.service.ingestion.DataIngestService
import io.violabs.mimir.core.common.Loggable
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/ingestion")
class DataIngestionController(private val dataIngestService: DataIngestService<TitleRequest, *>) : Loggable {
    @GetMapping("{title}")
    suspend fun getByTitle(@PathVariable title: String): ResponseEntity<DataIngestDocument> {
        log.info("Getting data by title: $title")
        val item = dataIngestService.getRawContent(TitleRequest(title))
        return ResponseEntity.ok(item)
    }
}