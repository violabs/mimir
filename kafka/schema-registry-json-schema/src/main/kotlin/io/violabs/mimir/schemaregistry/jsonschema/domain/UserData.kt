package io.violabs.mimir.schemaregistry.jsonschema.domain

data class UserData(
    val name: String,
    val email: String,
    val age: Int? = null,
    val preferences: UserPreferences? = null
)