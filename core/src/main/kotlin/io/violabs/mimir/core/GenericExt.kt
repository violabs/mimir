package io.violabs.mimir.core

fun <T> T?.defaultHashCode(multiplier: Int = 1): Int = this?.hashCode()?.let(multiplier::times) ?: 0