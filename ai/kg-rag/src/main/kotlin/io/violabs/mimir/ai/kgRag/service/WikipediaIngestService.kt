package io.violabs.mimir.ai.kgRag.service

import io.violabs.mimir.ai.kgRag.client.WikipediaClient
import io.violabs.mimir.ai.kgRag.domain.DataIngestTitleRequest
import io.violabs.mimir.ai.kgRag.domain.WikipediaDataIngestDTO
import org.springframework.stereotype.Service

@Service
class WikipediaIngestService(
    private val wikipediaClient: WikipediaClient
) : DataIngestService<DataIngestTitleRequest, WikipediaDataIngestDTO> {
    override suspend fun getContentWithTopicsIfAvailable(request: DataIngestTitleRequest): WikipediaDataIngestDTO {
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
}