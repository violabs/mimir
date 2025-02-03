package io.violabs.mimir.buildsrc.ai.ollamaTestStartup

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
    }

    inline fun <reified T> post(task: Task, builderScope: ProviderHttpBuilder.() -> Unit): T? = with(task) {
        val builder = ProviderHttpBuilder().apply(builderScope)

        val (url, body) = builder

        requireNotNull(url) { "URL must not be null." }
        requireNotNull(body) { "Body must not be null." }

        logger.debug("Starting POST. apiUrl: $url, body: $body")
        return tryCall({ body<T>() }) {
            client.post(url) {
                contentType(ContentType.Application.Json)
                setBody(body)
            }
        }
    }

    inline fun <reified T> get(task: Task, builderScope: BasicHttpBuilder.() -> Unit): T? = with(task) {
        val builder = BasicHttpBuilder().apply(builderScope)

        val (url) = builder

        requireNotNull(url) { "URL must not be null." }

        logger.debug("Starting GET. apiUrl: $url")
        return tryCall({ body<T>() }) {
            client.get(url) { contentType(ContentType.Application.Json) }
        }
    }

    inline fun <reified T> delete(task: Task, builderScope: ProviderHttpBuilder.() -> Unit) = with(task) {
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
            val response: HttpResponse = withTimeout(timeoutMs) { clientProvider(client) }
            logger.lifecycle("Response: $response")
            bodyExtractor.invoke(response)
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