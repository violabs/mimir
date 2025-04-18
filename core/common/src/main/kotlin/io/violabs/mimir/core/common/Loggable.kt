package io.violabs.mimir.core.common

import mu.two.KLogger
import mu.two.KLogging

private val K_LOGGING = KLogging()

private val LOG_MAP = mutableMapOf<String, KLogger>()

interface Loggable {
    val log: KLogger
        get() {
            val name = this::class.simpleName ?: "DefaultLogger"
            return LOG_MAP.getOrPut(name) { K_LOGGING.logger(name) }
        }

    suspend fun <T> trace(method: String, runnable: suspend LogContext.() -> T): T {
        return LogContext(method, log).runnable()
    }
}

class LogContext(
    private val methodName: String,
    private val logger: KLogger
) {
    fun log(message: String) {
        logger.info("[$methodName] $message")
    }
}