package io.violabs.mimir.plugins.ai.ollamaTestStartup

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeout
import kotlinx.serialization.json.Json
import org.gradle.api.Task

/**
 * A class to manage the various REST calls. The creation of this
 * class is restricted to a singleton.
 */
class HttpManager private constructor(clientOverride: HttpClient? = null) {
    val client: HttpClient = clientOverride ?: HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })
        }
        install(Logging) {
            level = LogLevel.BODY
        }
        engine {
            requestTimeout = 0 // No timeout, we'll handle it with withTimeout
            endpoint {
                connectTimeout = 5000
                keepAliveTime = 5000
                maxConnectionsCount = 1000
                pipelineMaxSize = 20
            }
        }
    }

    inline fun <reified T> post(
        task: Task,
        timeoutMs: Long = 5000,
        crossinline builderScope: ProviderHttpBuilder.() -> Unit
    ): T? = runBlocking {
        with(task) {
            val builder = ProviderHttpBuilder().apply(builderScope)

            val (url, body) = builder

            requireNotNull(url) { "URL must not be null." }
            requireNotNull(body) { "Body must not be null." }

            logger.debug("Starting POST. apiUrl: $url, body: $body")
            tryCall({ body<T>() }, timeoutMs) {
                client.post(url) {
                    contentType(ContentType.Application.Json)
                    setBody(body)
                }
            }
        }
    }

    inline fun <reified T> get(task: Task, crossinline builderScope: BasicHttpBuilder.() -> Unit): T? = runBlocking {
        with(task) {
            val builder = BasicHttpBuilder().apply(builderScope)

            val (url) = builder

            requireNotNull(url) { "URL must not be null." }

            logger.debug("Starting GET. apiUrl: $url")
            tryCall({ body<T>() }) {
                client.get(url) { contentType(ContentType.Application.Json) }
            }
        }
    }

    inline fun <reified T> delete(task: Task, crossinline builderScope: ProviderHttpBuilder.() -> Unit) = runBlocking {
        with(task) {
            val builder = ProviderHttpBuilder().apply(builderScope)

            val (url, body) = builder

            requireNotNull(url) { "URL must not be null." }

            logger.debug("Starting DELETE. apiUrl: $url, body: $body")
            tryCall({ body<T>() }) {
                client.delete(url) {
                    contentType(ContentType.Application.Json)
                    setBody(body)
                }
            }
        }
    }

    /**
     * Technically only used in the caller methods, but must be public due
     * to the reification.
     */
    fun <T> Task.tryCall(
        bodyExtractor: suspend HttpResponse.() -> T,
        timeoutMs: Long = 5000,
        clientProvider: suspend HttpClient.() -> HttpResponse,
    ): T? = runBlocking {
        try {
            val response: HttpResponse = try {
                withTimeout(timeoutMs) {
                    clientProvider(client)
                }
            } catch (e: kotlinx.coroutines.TimeoutCancellationException) {
                logger.error("Request timed out after ${timeoutMs}ms: ${e.message}")
                throw e  // Re-throw to be caught by outer try-catch
            } catch (e: Exception) {
                logger.error("Error in client request: ${e.message}")
                throw e  // Re-throw to be caught by outer try-catch
            }

            logger.lifecycle("Response: $response")
            try {
                bodyExtractor.invoke(response)
            } catch (e: Exception) {
                logger.error("Error extracting response body: ${e.message}")
                throw e
            }
        } catch (e: Exception) {
            logger.error("Error sending request: ${e.message}")
            e.printStackTrace()
            logger.debug("Completed call.")
            null
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