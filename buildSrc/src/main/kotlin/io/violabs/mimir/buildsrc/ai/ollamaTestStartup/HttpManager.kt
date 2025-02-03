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
import org.gradle.api.Task

class HttpManager private constructor(clientOverride: HttpClient? = null) {
    private val client: HttpClient = clientOverride ?: HttpClient(CIO) {
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

    fun post(task: Task, builderScope: ProviderHttpBuilder.() -> Unit) = with(task) {
        val builder = ProviderHttpBuilder().apply(builderScope)

        val (url, body) = builder

        requireNotNull(url) { "URL must not be null." }
        requireNotNull(body) { "Body must not be null." }

        logger.debug("Starting POST. apiUrl: $url, body: $body")
        tryCall {
            client.post(url) {
                contentType(ContentType.Application.Json)
                setBody(body)
            }
        }
    }

    fun get(task: Task, builderScope: BasicHttpBuilder.() -> Unit): HttpResponse? = with(task) {
        val builder = BasicHttpBuilder().apply(builderScope)

        val (url) = builder

        requireNotNull(url) { "URL must not be null." }

        logger.debug("Starting GET. apiUrl: $url")
        return tryCall {
            client.get(url) { contentType(ContentType.Application.Json) }
        }
    }

    fun delete(task: Task, builderScope: ProviderHttpBuilder.() -> Unit) = with(task) {
        val builder = ProviderHttpBuilder().apply(builderScope)

        val (url, body) = builder

        requireNotNull(url) { "URL must not be null." }

        logger.debug("Starting DELETE. apiUrl: $url, body: $body")
        tryCall {
            client.delete(url) {
                contentType(ContentType.Application.Json)
                setBody(body)
            }
        }
    }

    private fun Task.tryCall(clientProvider: suspend HttpClient.() -> HttpResponse): HttpResponse? = runBlocking {
        try {
            val response: HttpResponse = clientProvider(client)
            logger.lifecycle("Response: $response")
            response
        } catch (e: Exception) {
            logger.error("Error sending request: ${e.message}")
            e.printStackTrace()
            null
        } finally {
            client.close()
            logger.debug("Completed call.")
        }
    }

    /**
     * Provides content with the request. [POST, PUT, PATCH, DELETE]
     */
    class ProviderHttpBuilder(
        url: String? = null,
        var body: String? = null
    ) : BasicHttpBuilder(url) {
        operator fun component2() = body
    }

    open class BasicHttpBuilder(var url: String? = null) {
        operator fun component1() = url
    }

    companion object {
        private var instance: HttpManager = HttpManager()

        fun configure(client: HttpClient) {
            instance = HttpManager(client)
        }

        fun instance(): HttpManager = instance
    }
}