package io.violabs.mimir.plugins.ai.ollamaTestStartup

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.register

class OllamaTestStartupPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        target.tasks.register<PullOnStartupTask>("pullOnStartup") {
            port = 11435
            model = "nomic-embed-text:latest"
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