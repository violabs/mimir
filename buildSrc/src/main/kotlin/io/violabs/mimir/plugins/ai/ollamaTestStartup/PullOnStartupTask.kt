package io.violabs.mimir.plugins.ai.ollamaTestStartup

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction

open class PullOnStartupTask : OllamaModelTask() {
    @Input var protocol: String = "http"
    @Input var host: String     = "localhost"
    @Input var port: Int        = 11434
    @Input override var model: String? = null

    @TaskAction fun pullOnStartup() {
        requireNotNull(model) { "Model name must sit here" }
        logger.lifecycle("Checking existence of: $model")

        val client = OkHttpClient()
        val mapper = ObjectMapper().disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
        val tagsUri = "$protocol://$host:$port/api/tags"
        val tagRequest = Request.Builder().url(tagsUri).build()

        // 1) First existence check
        if (checkModelExists(client, tagRequest, mapper)) {
            logger.lifecycle("$model already present – skipping pull")
            return
        }

        // 2) Kick off pull
        logger.lifecycle("Requesting pull for: $model")
        val pullUri = "$protocol://$host:$port/api/pull"
        val pullBody = modelJson().toRequestBody(
            "application/json; charset=utf-8".toMediaType()
        )
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

        if (checkModelExists(client, tagRequest, mapper)) {
            logger.lifecycle("Model now available: $model")
            return
        } else {
            logger.lifecycle("Failed to pull model: $model")
        }
    }

    private fun checkModelExists(
        client: OkHttpClient,
        request: Request,
        mapper: ObjectMapper
    ): Boolean {
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
}
