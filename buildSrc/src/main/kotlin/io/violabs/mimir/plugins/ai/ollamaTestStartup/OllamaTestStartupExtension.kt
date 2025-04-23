package io.violabs.mimir.plugins.ai.ollamaTestStartup

open class OllamaTestStartupExtension {
    val models: MutableList<String> = mutableListOf()
    var withDefault: Boolean = true

    fun models(vararg models: String) {
        this.models.addAll(models)
    }
}