package io.violabs.mimir.ai.kgRag.client

import io.violabs.mimir.ai.kgRag.domain.NERResponse
import io.violabs.mimir.core.common.Loggable
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBodyOrNull
import org.springframework.web.util.UriComponentsBuilder
import java.net.URI

@Component
class NermalClient(
    private val webClient: WebClient,
    @Value("\${app.clients.nermal.base-url}")
    private var baseUrl: String
) : Loggable {
    private val uri: URI = UriComponentsBuilder
        .fromUriString("$baseUrl/ner")
        .build()
        .toUri()

    suspend fun determineNamedEntities(content: String): NERResponse? {
        val body = mapOf("text" to content)

        return webClient
            .post()
            .uri(uri)
            .bodyValue(body)
            .retrieve()
            .awaitBodyOrNull<NERResponse>()
    }
}