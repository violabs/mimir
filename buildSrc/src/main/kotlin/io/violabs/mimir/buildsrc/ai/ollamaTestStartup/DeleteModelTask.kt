package io.violabs.mimir.buildsrc.ai.ollamaTestStartup

import kotlinx.coroutines.runBlocking
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

        runBlocking {
            HttpManager.instance().delete<Unit>(self) {
                url = apiUrl
                body = modelJson()
            }
        }
    }
}