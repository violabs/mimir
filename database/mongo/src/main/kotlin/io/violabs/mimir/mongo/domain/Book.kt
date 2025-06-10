package io.violabs.mimir.mongo.domain

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document(collection = "books")
data class Book(
    @Id
    val id: String? = null,
    val title: String,
    val author: String,
    val isbn: String? = null,
    val publishedDate: LocalDateTime? = null,
    val description: String? = null,
    val chapters: List<Chapter> = emptyList(),
    val metadata: BookMetadata? = null,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now()
)

data class Chapter(
    val id: String,
    val title: String,
    val chapterNumber: Int,
    val summary: String? = null,
    val components: List<ChapterComponent> = emptyList(),
    val wordCount: Int = 0,
    val createdAt: LocalDateTime = LocalDateTime.now()
)

// Sealed class for different types of chapter components
sealed class ChapterComponent {
    abstract val id: String
    abstract val order: Int
    abstract val createdAt: LocalDateTime
}

data class TextSection(
    override val id: String,
    override val order: Int,
    val content: String,
    val sectionType: TextSectionType = TextSectionType.PARAGRAPH,
    override val createdAt: LocalDateTime = LocalDateTime.now()
) : ChapterComponent()

data class ImageSection(
    override val id: String,
    override val order: Int,
    val imageUrl: String,
    val caption: String? = null,
    val altText: String? = null,
    override val createdAt: LocalDateTime = LocalDateTime.now()
) : ChapterComponent()

data class CodeSection(
    override val id: String,
    override val order: Int,
    val code: String,
    val language: String,
    val description: String? = null,
    override val createdAt: LocalDateTime = LocalDateTime.now()
) : ChapterComponent()

data class QuoteSection(
    override val id: String,
    override val order: Int,
    val quote: String,
    val author: String? = null,
    val source: String? = null,
    override val createdAt: LocalDateTime = LocalDateTime.now()
) : ChapterComponent()

enum class TextSectionType {
    PARAGRAPH, HEADING, SUBHEADING, BULLET_POINT, NUMBERED_LIST
}

data class BookMetadata(
    val genre: String? = null,
    val tags: List<String> = emptyList(),
    val language: String = "en",
    val totalWordCount: Int = 0,
    val estimatedReadingTime: Int = 0 // in minutes
)