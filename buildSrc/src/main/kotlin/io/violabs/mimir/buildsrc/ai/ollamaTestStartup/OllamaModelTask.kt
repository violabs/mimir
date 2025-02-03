package io.violabs.mimir.buildsrc.ai.ollamaTestStartup

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input

abstract class OllamaModelTask : DefaultTask() {
    @get:Input
    abstract var model: String?

    init {
        group = "ollama"
    }

    fun modelJson(): String {
        requireNotNull(model) { "Model not found. Please provide a model." }
        return """{"model": "$model"}"""
    }
}