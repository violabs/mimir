package io.violabs.mimir.plugins.ai.ollamaTestStartup

import io.violabs.mimir.plugins.ai.ollamaTestStartup.tasks.DeleteModelTask
import io.violabs.mimir.plugins.ai.ollamaTestStartup.tasks.GetModelsTask
import io.violabs.mimir.plugins.ai.ollamaTestStartup.tasks.PullOnStartupTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.register

private val DEFAULT_MODEL_LIST = listOf("nomic-embed-text:latest")

class OllamaTestStartupPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        val extension = target.extensions.create<OllamaTestStartupExtension>("ollamaTestStartup")

        target.tasks.register<PullOnStartupTask>("pullOnStartup") {
            port = 11435

            val chosenModels = if (extension.models.isEmpty()) {
                DEFAULT_MODEL_LIST
            } else if (extension.withDefault) {
                DEFAULT_MODEL_LIST + extension.models
            } else {
                extension.models
            }

            models = chosenModels
        }

        target.tasks.register<GetModelsTask>("getModels") {
            port = 11435
        }

        target.tasks.register<DeleteModelTask>("deleteModel") {
            port = 11435
            model = "nomic-embed-text:latest"
        }
    }
}