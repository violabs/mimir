package io.violabs.mimir.buildsrc.ai.ollamaTestStartup

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.register

class OllamaTestStartupPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        target.tasks.register<PullOnStartupTask>("pullOnStartup") {
            port = 11435
            model = "nomic-embed-text:latest"
        }

        target.tasks.register<DeleteModelTask>("deleteModel") {
            port = 11435
            model = "nomic-embed-text:latest"
        }
    }
}