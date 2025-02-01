package io.violabs.mimir.buildsrc.ai.ollamaTestStartup

import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction

open class PullOnStartupTask : SendToRestApiTask() {
    //http://mimir-ollama:11434/api/pull
    //{\"name\":\"nomic-embed-text:latest\"}
    @Input
    var model: String = "nomic-embed-text:latest"

    @Input
    var useCustomUrl: Boolean = false

    init {
        if (!useCustomUrl && apiUrl != "localhost:8080") {
            logger.warn("Overriding provided url. provided: $apiUrl, default: localhost:11434/api/pull")
            apiUrl = "localhost:11434/api/pull"
        }
    }

    @TaskAction
    fun pullOnStartup() {
        val json = """
            {"name": "$model"}
        """.trimIndent()

        logger.debug("Pulling model")

        super.content = json

        super.sendToRestApi()
    }
}