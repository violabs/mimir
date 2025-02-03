package io.violabs.mimir.buildsrc.ai.ollamaTestStartup

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
        logger.debug("Pulling model. name: $model")

        val pullApiUrl = "$protocol://$host:$port/api/pull"

        val httpManager = HttpManager.instance()

        httpManager.post<Unit>(this) {
            url = pullApiUrl
            body = modelJson()
        }

        val getApiUrl = "$protocol://$host:$port/api/tags"

        val modelFound = httpManager
            .get<ModelResponse>(this) { url = getApiUrl }
            ?.models
            ?.onEach { println(it) }
            ?.any { it.model == model }
            ?: false

        if (!modelFound) throw OllamaException("Unable to pull model: $model")
    }
}