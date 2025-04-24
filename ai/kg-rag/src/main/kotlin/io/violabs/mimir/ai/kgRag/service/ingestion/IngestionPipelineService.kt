package io.violabs.mimir.ai.kgRag.service.ingestion

import io.violabs.mimir.ai.kgRag.domain.DataIngestDocument
import io.violabs.mimir.ai.kgRag.domain.api.TitleRequest
import io.violabs.mimir.ai.kgRag.service.QueryService
import org.springframework.stereotype.Service

@Service
class IngestionPipelineService(
    private val dataIngestService: DataIngestService<TitleRequest, *>,
    private val queryService: QueryService
) {
    suspend fun processIntake(request: TitleRequest): DataIngestDocument {
        val doc = dataIngestService.gatherAndSave(request)

        queryService.addContent(doc)

        return doc
    }
}