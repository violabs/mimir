package io.violabs.mimir.buildsrc.ai.ollamaTestStartup

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
        logger.debug("Getting models.")

        val apiUrl = "$protocol://$host:$port/api/tags"

        HttpManager
            .instance()
            .get<List<OllamaTag>>(this) { url = apiUrl }
            ?.forEach { logger.lifecycle("model: $it") }
    }
}