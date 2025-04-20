package io.violabs.mimir.plugins.ai.ollamaTestStartup.tasks

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import io.violabs.mimir.plugins.ai.ollamaTestStartup.models.ModelResponse
import io.violabs.mimir.plugins.ai.ollamaTestStartup.models.OllamaException
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.gradle.api.DefaultTask
import org.gradle.api.logging.Logger
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction

open class PullOnStartupTask : DefaultTask() {
    @Input
    var protocol: String = "http"
    @Input
    var host: String = "localhost"
    @Input
    var port: Int = 11434
    @Input
    var models: List<String>? = null

    init {
        group = "ollama"
    }

    @TaskAction
    fun pullOnStartup() {
        require(models?.isNotEmpty() == true) {
            "Models are required. Please supply at least one model to pull the on-start"
        }

        val client = OkHttpClient()
        val mapper = ObjectMapper().disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
        val baseUrl = "$protocol://$host:$port"

        models
            ?.map { ModelPullProcess(it, baseUrl, client, mapper, logger) }
            ?.forEach(ModelPullProcess::pullModelAndConfirm)
    }

    class ModelPullProcess(
        val model: String,
        val baseUrl: String,
        val client: OkHttpClient,
        val mapper: ObjectMapper,
        val logger: Logger
    ) {
        fun pullModelAndConfirm() {
            logger.lifecycle("Checking existence of: $model")

            val tagsUri = "$baseUrl/api/tags"
            val tagRequest = Request.Builder().url(tagsUri).build()

            // 1) First existence check
            if (checkModelExists(tagRequest)) {
                logger.lifecycle("$model already present – skipping pull")
                return
            }

            // 2) Kick off pull
            triggerModelPull()

            if (checkModelExists(tagRequest)) {
                logger.lifecycle("Model now available: $model")
                return
            } else {
                logger.lifecycle("Failed to pull model: $model")
            }
        }

        private fun triggerModelPull() {
            logger.lifecycle("Requesting pull for: $model")
            val pullUri = "$baseUrl/api/pull"
            val pullBody = modelJsonRequestBody()
            val pullReq = Request.Builder()
                .url(pullUri)
                .post(pullBody)
                .build()

            client.newCall(pullReq).execute().use { resp ->
                if (!resp.isSuccessful) {
                    throw OllamaException("Pull request failed (code ${resp.code})")
                }

                val reader = resp.body.charStream()
                reader.buffered().forEachLine { line ->
                    logger.debug("OLLAMA: $line")
                }
            }
        }

        private fun checkModelExists(request: Request): Boolean {
            client.newCall(request).execute().use { resp ->
                return if (resp.isSuccessful) {
                    val bodyBytes = resp.body.bytes()
                    val models = mapper.readValue(bodyBytes, ModelResponse::class.java)
                    models.models?.any { it.model == model } ?: false
                } else {
                    logger.error("Tag endpoint error: code ${resp.code}")
                    false
                }
            }
        }

        private fun modelJsonRequestBody(): RequestBody {
            return """{"model": "$model"}""".toRequestBody(
                "application/json; charset=utf-8".toMediaType()
            )
        }
    }
}
