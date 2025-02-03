package io.violabs.mimir.buildsrc.ai.ollamaTestStartup

import kotlinx.serialization.Serializable

@Serializable
data class ModelResponse(val models: List<OllamaTag>)

@Serializable
data class OllamaTag(val model: String? = null)