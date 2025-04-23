package io.violabs.mimir.ai.kgRag.domain.client.wikipedia

import com.fasterxml.jackson.annotation.JsonProperty

data class WikipediaContentResponse(
    val query: Query? = null,
    @JsonProperty("batchcomplete")
    val batchComplete: String? = null
) {
    data class Query(
        @JsonProperty("normalized")
        val normalizations: List<Normalization>? = null,
        val pages: Map<String, Page>? = null
    ) {
        data class Normalization(
            val from: String? = null,
            val to: String? = null
        )

        data class Page(
            @JsonProperty("pageid")
            val pageId: String? = null,
            val ns: Int? = null,
            val title: String? = null,
            val extract: String? = null
        )
    }
}