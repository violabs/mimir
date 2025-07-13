package io.violabs.mimir.schemaregistry.jsonschema.domain

data class UserPreferences(
    val theme: String = "light",
    val notifications: Boolean = true,
    val language: String = "en"
)