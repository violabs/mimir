package io.violabs.mimir.plugins.docker

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import org.gradle.process.ExecOperations
import java.io.ByteArrayOutputStream
import javax.inject.Inject

open class CheckRunningContainersTask @Inject constructor(
    private val execOperations: ExecOperations
) : DefaultTask() {

    @TaskAction
    fun checkRunningContainers() {
        val outputStream = ByteArrayOutputStream()
        execOperations.exec {
            commandLine("docker", "ps")
            standardOutput = outputStream
        }

        val results = outputStream.toString("UTF-8")

        logger.lifecycle("--- CONTAINERS RUNNING [docker ps] ---")
        logger.lifecycle(results)
        logger.lifecycle("--------------------------------------")
    }
}