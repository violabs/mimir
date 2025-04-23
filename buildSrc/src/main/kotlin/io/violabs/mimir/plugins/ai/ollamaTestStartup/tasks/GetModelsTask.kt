package io.violabs.mimir.plugins.ai.ollamaTestStartup.tasks

import com.fasterxml.jackson.databind.ObjectMapper
import io.violabs.mimir.plugins.ai.ollamaTestStartup.models.ModelResponse
import okhttp3.OkHttpClient
import okhttp3.Request
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction

open class GetModelsTask : DefaultTask() {
    init {
        group = "ollama"
    }

    @Input
    var protocol: String = "http"

    @Input
    var host: String = "localhost"

    @Input
    var port: Int = 11434

    @TaskAction
    fun getModels() {
        logger.info("Getting models.")

        val client = OkHttpClient()

        val objectMapper = ObjectMapper()

        val apiUrl = "$protocol://$host:$port/api/tags"

        val request = Request.Builder().url(apiUrl).build()

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) {
                objectMapper.readValue(response.body.bytes(), ModelResponse::class.java)
            } else {
                logger.error("Failed to get models. status code: ${response.code}")
            }
        }
    }
}