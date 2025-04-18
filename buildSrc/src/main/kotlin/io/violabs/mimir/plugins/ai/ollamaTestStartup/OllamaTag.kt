package io.violabs.mimir.plugins.ai.ollamaTestStartup

import kotlinx.serialization.Serializable

@Serializable
data class ModelResponse(val models: List<OllamaTag>? = null)

@Serializable
data class OllamaTag(val model: String? = null)