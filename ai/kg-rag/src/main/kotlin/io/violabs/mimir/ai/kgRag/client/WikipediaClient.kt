package io.violabs.mimir.ai.kgRag.client

import io.violabs.mimir.ai.kgRag.domain.WikipediaContentResponse
import io.violabs.mimir.core.common.Loggable
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBodyOrNull
import org.springframework.web.util.UriComponentsBuilder
import java.net.URI

@Component
class WikipediaClient(
    private val webClient: WebClient,
    @Value("\${app.clients.wikipedia.base-url}")
    private var baseUrl: String
) : Loggable {
    private val baseUriComponentsBuilder: UriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(baseUrl)
    private val contentUriBuilder: UriComponentsBuilder = baseUriComponentsBuilder
        .queryParam("action", "query")
        .queryParam("format", "json")
        .queryParam("prop", "extracts")
        .queryParam("explaintext")

    suspend fun getContentByTitle(title: String): WikipediaContentResponse? {
        val uri: URI = contentUriBuilder
            .queryParam("titles", title)
            .build()
            .toUri()

        log.info("uri: $uri")

        return webClient
            .get()
            .uri(uri)
            .retrieve()
            .awaitBodyOrNull<WikipediaContentResponse>()
    }
}