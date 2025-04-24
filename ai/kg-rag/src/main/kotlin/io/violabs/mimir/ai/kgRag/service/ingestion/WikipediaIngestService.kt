package io.violabs.mimir.ai.kgRag.service.ingestion

import io.violabs.mimir.ai.kgRag.client.WikipediaClient
import io.violabs.mimir.ai.kgRag.domain.api.TitleRequest
import io.violabs.mimir.ai.kgRag.domain.client.wikipedia.WikipediaDataIngestDTO
import io.violabs.mimir.ai.kgRag.domain.entity.ProcessDoc
import io.violabs.mimir.ai.kgRag.service.DocumentChunkService
import org.springframework.stereotype.Service

@Service
class WikipediaIngestService(
    private val documentChunkService: DocumentChunkService,
    private val wikipediaClient: WikipediaClient
) : DataIngestService<TitleRequest, WikipediaDataIngestDTO> {
    override suspend fun gatherAndSave(request: TitleRequest): WikipediaDataIngestDTO {
        val title = requireNotNull(request.title) { "title is required" }
        val content = request.content ?: gatherWikiArticle(title)

        val doc = documentChunkService.chunkAndSave(ProcessDoc(title), content)
        return WikipediaDataIngestDTO(title, doc = doc)
    }

    override suspend fun getRawContent(request: TitleRequest): WikipediaDataIngestDTO {
        val title = requireNotNull(request.title) { "title is required" }
        val contentResponse = wikipediaClient
            .getContentByTitle(title)
            ?.query
            ?.pages
            ?.values
            ?.firstOrNull()
            ?.extract
        return WikipediaDataIngestDTO(title, contentResponse)
    }

    private suspend fun gatherWikiArticle(title: String): String {
        val response = wikipediaClient.getContentByTitle(title)
        return response
            ?.query
            ?.pages
            ?.values
            ?.firstOrNull()
            ?.extract
            ?: ""
    }
}