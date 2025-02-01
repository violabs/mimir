package io.violabs.mimir.buildsrc.ai.ollamaTestStartup

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.register

class OllamaTestStartupPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        target.tasks.register<PullOnStartupTask>("pullOnStartup") {
            group = "setup"
            useCustomUrl = true
            apiUrl = "localhost:11435/api/pull"
        }
    }
}