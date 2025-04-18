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
            if (resp.isSuccessful) {
                logger.lifecycle("Pull API accepted (code ${resp.code})")
            } else {
                throw OllamaException("Pull request failed (code ${resp.code})")
            }
        }

        // 3) Poll until the model shows up
        val maxRetries = 10
        var attempt = 0
        while (attempt < maxRetries) {
            if (checkModelExists(client, tagRequest, mapper)) {
                logger.lifecycle("Model now available: $model")
                return
            }

            attempt++
            logger.lifecycle("Still pulling… attempt $attempt/$maxRetries")
            Thread.sleep(2_000)  // wait 2s before retry
        }

        throw OllamaException("Model pull timed out after $maxRetries attempts: $model")
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
