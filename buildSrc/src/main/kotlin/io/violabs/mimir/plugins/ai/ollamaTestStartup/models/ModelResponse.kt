package io.violabs.mimir.plugins.ai.ollamaTestStartup.models

import kotlinx.serialization.Serializable

@Serializable
data class ModelResponse(val models: List<OllamaTag>? = null)