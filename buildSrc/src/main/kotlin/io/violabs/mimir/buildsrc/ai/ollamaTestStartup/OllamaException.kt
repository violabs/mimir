package io.violabs.mimir.buildsrc.ai.ollamaTestStartup

class OllamaException(message: String, ex: Exception? = null) : Exception("[OLLAMA ERROR] $message", ex)