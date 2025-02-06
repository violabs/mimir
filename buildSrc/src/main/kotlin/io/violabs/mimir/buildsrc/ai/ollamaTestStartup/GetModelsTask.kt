package io.violabs.mimir.buildsrc.ai.ollamaTestStartup

import kotlinx.coroutines.runBlocking
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction

open class GetModelsTask : DefaultTask() {
    @Input
    var protocol: String = "http"

    @Input
    var host: String = "localhost"

    @Input
    var port: Int = 11434

    @TaskAction
    fun getModels() {
        logger.info("Getting models.")

        val apiUrl = "$protocol://$host:$port/api/tags"

        val self = this

        runBlocking {
            HttpManager
                .instance()
                .get<ModelResponse>(self) { url = apiUrl }
                ?.models
                ?.forEach { logger.lifecycle("model: $it") }
        }
    }
}