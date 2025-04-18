package io.violabs.mimir.plugins.ai.ollamaTestStartup

class OllamaException(message: String, ex: Exception? = null) : Exception("[OLLAMA ERROR] $message", ex)