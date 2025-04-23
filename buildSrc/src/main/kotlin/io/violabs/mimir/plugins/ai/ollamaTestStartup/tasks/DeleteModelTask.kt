package io.violabs.mimir.plugins.ai.ollamaTestStartup.tasks

import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction

open class DeleteModelTask : OllamaModelTask() {
    @Input
    var protocol: String = "http"

    @Input
    var host: String = "localhost"

    @Input
    var port: Int = 11434

    @Input
    override var model: String? = null

    @TaskAction
    fun deleteModel() {
        logger.debug("Deleting model. name: $model")

        val apiUrl = "$protocol://$host:$port/api/delete"

        val self = this

        val client = OkHttpClient()

        val requestBody = modelJson().toRequestBody(
            "application/json; charset=utf-8".toMediaType()
        )

        val request = Request.Builder().url(apiUrl).delete(requestBody).build()

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) {
                logger.info("Successfully deleted model: $model")
            } else {
                logger.error("Failed to get models. status code: ${response.code}")
            }
        }
    }
}