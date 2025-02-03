package io.violabs.mimir.buildsrc.ai.ollamaTestStartup

import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction

open class DeleteModelTask : OllamaTask() {
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

        HttpManager.instance().delete(this) {
            url = apiUrl
            body = modelJson()
        }
    }
}