package io.violabs.mimir.buildsrc.ai.ollamaTestStartup

import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction

open class GetModelsTask : OllamaTask() {
    @Input
    var protocol: String = "http"

    @Input
    var host: String = "localhost"

    @Input
    var port: Int = 11434

    override var model: String? = null

    @TaskAction
    fun deleteModel() {
        logger.debug("Getting models.")

        val apiUrl = "$protocol://$host:$port/api/tags"

        HttpManager.instance().delete(this) {
            url = apiUrl
            body = modelJson()
        }
    }
}