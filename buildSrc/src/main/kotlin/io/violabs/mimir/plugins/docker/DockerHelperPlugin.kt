package io.violabs.mimir.plugins.docker

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.register

class DockerHelperPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.tasks.register<CheckRunningContainersTask>("checkRunningContainers") {
            group = "pipeline"
        }
    }
}