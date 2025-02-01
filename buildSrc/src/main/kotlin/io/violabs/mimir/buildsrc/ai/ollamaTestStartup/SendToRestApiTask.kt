package io.violabs.mimir.buildsrc.ai.ollamaTestStartup

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction

/**
 * Task to send a JSON file to a REST API
 */
open class SendToRestApiTask : DefaultTask() {

    @Input
    var apiUrl: String = "localhost:8080"

    @Input
    var content: String = "{}"

    @TaskAction
    fun sendToRestApi() {
        logger.debug("Starting call. apiUrl: $apiUrl, content: $content")
        runBlocking {
            val client = HttpClient(CIO) {
                install(ContentNegotiation) {
                    json(Json {
                        prettyPrint = true
                        isLenient = true
                    })
                }
                install(Logging) {
                    level = LogLevel.BODY
                }
            }

            try {
                val response: HttpResponse = client.post(apiUrl) {
                    contentType(ContentType.Application.Json)
                    setBody(content)
                }
                logger.lifecycle("Response: $response")
            } catch (e: Exception) {
                logger.error("Error sending request: ${e.message}")
                e.printStackTrace()
            } finally {
                client.close()
                logger.debug("Completed call.")
            }
        }
    }
}
