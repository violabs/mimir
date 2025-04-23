package io.violabs.mimir.ai.kgRag.service.ingestion

import io.violabs.mimir.ai.kgRag.client.WikipediaClient
import io.violabs.mimir.ai.kgRag.domain.api.TitleRequest
import io.violabs.mimir.ai.kgRag.domain.client.wikipedia.WikipediaDataIngestDTO
import io.violabs.mimir.ai.kgRag.service.TopicService
import org.springframework.stereotype.Service

@Service
class WikipediaIngestService(
    private val wikipediaClient: WikipediaClient,
    private val topicService: TopicService
) : DataIngestService<TitleRequest, WikipediaDataIngestDTO> {
    override suspend fun getContentWithTopicsIfAvailable(request: TitleRequest): WikipediaDataIngestDTO {
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

    private fun chunkBySection(content: String): List<String> {
        return content.split("\n== . ==\n".toRegex())
    }
}