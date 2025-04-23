package io.violabs.mimir.ai.kgRag.domain.client.ner

import com.fasterxml.jackson.annotation.JsonProperty

data class NERResponse(val entities: List<Entity>? = null) {
    data class Entity(
        @JsonProperty("end_char")
        val endChar: Int? = null,
        val label: NERLabel? = null,
        val startChar: Int? = null,
        val text: String? = null
    )
}