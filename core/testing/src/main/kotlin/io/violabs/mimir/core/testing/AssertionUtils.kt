package io.violabs.mimir.core.testing

fun <T> assertExpected(expected: T, actual: T, message: String? = null) {
    val loggedMessage = message ?: "EXPECT: $expected\nACTUAL: $actual"

    assert(expected == actual) {
        loggedMessage
    }
}