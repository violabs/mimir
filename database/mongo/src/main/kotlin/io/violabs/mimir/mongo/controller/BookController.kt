package io.violabs.mimir.mongo.controller

import io.violabs.mimir.mongo.domain.Book
import io.violabs.mimir.mongo.domain.CodeSection
import io.violabs.mimir.mongo.domain.ImageSection
import io.violabs.mimir.mongo.domain.QuoteSection
import io.violabs.mimir.mongo.domain.TextSection
import io.violabs.mimir.mongo.domain.TextSectionType
import io.violabs.mimir.mongo.service.BookService
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/api/books")
class BookController(private val bookService: BookService) {

    @PostMapping
    fun createBook(@RequestBody request: CreateBookRequest): Book {
        return bookService.createBook(request.title, request.author)
    }

    @PostMapping("/{bookId}/chapters")
    fun addChapter(
        @PathVariable bookId: String,
        @RequestBody request: CreateChapterRequest
    ): Book? {
        return bookService.addChapter(bookId, request.title, request.chapterNumber)
    }

    @PostMapping("/{bookId}/chapters/{chapterId}/components")
    fun addComponent(
        @PathVariable bookId: String,
        @PathVariable chapterId: String,
        @RequestBody request: AddComponentRequest
    ): Book? {
        val component = when (request.type) {
            AddComponentRequest.Type.TEXT -> TextSection(
                id = UUID.randomUUID().toString(),
                order = request.order,
                content = request.content,
                sectionType = request.sectionType ?: TextSectionType.PARAGRAPH
            )
            AddComponentRequest.Type.IMAGE -> ImageSection(
                id = UUID.randomUUID().toString(),
                order = request.order,
                imageUrl = request.imageUrl!!,
                caption = request.caption
            )
            AddComponentRequest.Type.CODE -> CodeSection(
                id = UUID.randomUUID().toString(),
                order = request.order,
                code = request.code!!,
                language = request.language!!,
                description = request.description
            )
            AddComponentRequest.Type.QUOTE -> QuoteSection(
                id = UUID.randomUUID().toString(),
                order = request.order,
                quote = request.quote!!,
                author = request.quoteAuthor,
                source = request.source
            )
        }

        return bookService.addComponentToChapter(bookId, chapterId, component)
    }
}

data class CreateBookRequest(val title: String, val author: String)
data class CreateChapterRequest(val title: String, val chapterNumber: Int)
data class AddComponentRequest(
    val type: Type,
    val order: Int,
    val content: String = "",
    val sectionType: TextSectionType? = null,
    val imageUrl: String? = null,
    val caption: String? = null,
    val code: String? = null,
    val language: String? = null,
    val description: String? = null,
    val quote: String? = null,
    val quoteAuthor: String? = null,
    val source: String? = null
) {
    enum class Type { CODE, QUOTE, TEXT, IMAGE }
}