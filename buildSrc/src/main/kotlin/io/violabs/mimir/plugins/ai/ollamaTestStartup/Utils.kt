package io.violabs.mimir.plugins.ai.ollamaTestStartup

import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.Property


inline fun <reified T> ObjectFactory.default(item: T): Property<T> = this.property(T::class.java).convention(item)