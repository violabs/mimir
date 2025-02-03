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

        val apiUrl = "$protocol://$host:$port/api/pull"

        val httpManager = HttpManager.instance()

        httpManager.post<Unit>(this) {
            url = apiUrl
            body = modelJson()
        }
    }

    //{
    //            "name": "nomic-embed-text:latest",
    //            "model": "nomic-embed-text:latest",
    //            "modified_at": "2025-02-03T03:04:31.235356003Z",
    //            "size": 274302450,
    //            "digest": "0a109f422b47e3a30ba2b10eca18548e944e8a23073ee3f3e947efcf3c45e59f",
    //            "details": {
    //                "parent_model": "",
    //                "format": "gguf",
    //                "family": "nomic-bert",
    //                "families": [
    //                    "nomic-bert"
    //                ],
    //                "parameter_size": "137M",
    //                "quantization_level": "F16"
    //            }
    //        }
}