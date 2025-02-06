package io.violabs.mimir.buildsrc.ai.ollamaTestStartup

import kotlinx.coroutines.runBlocking
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction

open class PullOnStartupTask : OllamaModelTask() {
    @Input
    var protocol: String = "http"

    @Input
    var host: String = "localhost"

    @Input
    var port: Int = 11434

    @Input
    override var model: String? = null

    @TaskAction
    fun pullOnStartup() {
        requireNotNull(model) { "Model name must be provided" }
        logger.lifecycle("Checking if model exists: $model")

        val httpManager = HttpManager.instance()
        try {
            val getApiUrl = "$protocol://$host:$port/api/tags"

        // First check if model exists
        val self = this

        runBlocking {
            val modelExists = httpManager
                .get<ModelResponse>(self) { url = getApiUrl }
                ?.models
                ?.any { it.model == model }
                ?: false


            if (modelExists) {
                logger.lifecycle("Model already exists: $model")
                return@runBlocking
            }

            logger.lifecycle("Pulling model: $model")
            val pullApiUrl = "$protocol://$host:$port/api/pull"

            try {
                httpManager.post<Unit>(self) {
                    url = pullApiUrl
                    body = modelJson()
                }

                // Verify model was pulled
                val modelPulled = httpManager
                    .get<ModelResponse>(self) { url = getApiUrl }
                    ?.models
                    ?.any { it.model == model }
                    ?: false

                if (!modelPulled) throw OllamaException("Model pull completed but model not found: $model")

                logger.lifecycle("Successfully pulled model: $model")
            } catch (e: Exception) {
                logger.error("Failed to pull model: $model")
                throw OllamaException("Failed to pull model: $model", e)
            }
        }
        } finally {
            httpManager.client.close()
        }
    }
}