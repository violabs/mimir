package io.violabs.mimir.graphql.async.config

import com.fasterxml.jackson.core.JsonProcessingException
import io.violabs.mimir.core.common.Loggable
import io.violabs.mimir.graphql.async.common.OBJECT_MAPPER
import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter

@Converter
class JsonConverter<T> : AttributeConverter<T, String>, Loggable {
    override fun convertToDatabaseColumn(attribute: T?): String? {
        if (attribute == null) return null
        
        return try {
            OBJECT_MAPPER.writeValueAsString(attribute)
        } catch (e: JsonProcessingException) {
            log.error("Error converting to JSON", e)
            null
        }
    }

    override fun convertToEntityAttribute(dbData: String?): T? {
        if (dbData == null) return null
        
        return try {
            @Suppress("UNCHECKED_CAST")
            OBJECT_MAPPER.readValue(dbData, Object::class.java) as T
        } catch (e: JsonProcessingException) {
            log.error("Error converting from JSON", e)
            null
        }
    }
}