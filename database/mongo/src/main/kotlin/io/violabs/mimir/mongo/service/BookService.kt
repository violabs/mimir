package io.violabs.mimir.mongo.service

import io.violabs.mimir.mongo.domain.Book
import io.violabs.mimir.mongo.domain.Chapter
import io.violabs.mimir.mongo.domain.ChapterComponent
import io.violabs.mimir.mongo.domain.CodeSection
import io.violabs.mimir.mongo.domain.ImageSection
import io.violabs.mimir.mongo.domain.QuoteSection
import io.violabs.mimir.mongo.domain.TextSection
import io.violabs.mimir.mongo.repo.BookRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.UUID

@Service
class BookService(private val bookRepository: BookRepository) {
    
    fun createBook(title: String, author: String): Book {
        val book = Book(
            title = title,
            author = author
        )
        return bookRepository.save(book)
    }
    
    fun addChapter(bookId: String, chapterTitle: String, chapterNumber: Int): Book? {
        val book = bookRepository.findById(bookId).orElse(null) ?: return null
        
        val newChapter = Chapter(
            id = UUID.randomUUID().toString(),
            title = chapterTitle,
            chapterNumber = chapterNumber
        )
        
        val updatedChapters = book.chapters + newChapter
        val updatedBook = book.copy(
            chapters = updatedChapters,
            updatedAt = LocalDateTime.now()
        )
        
        return bookRepository.save(updatedBook)
    }
    
    fun addComponentToChapter(
        bookId: String, 
        chapterId: String, 
        component: ChapterComponent
    ): Book? {
        val book = bookRepository.findById(bookId).orElse(null) ?: return null
        
        val updatedChapters = book.chapters.map { chapter ->
            if (chapter.id == chapterId) {
                val updatedComponents = chapter.components + component
                val wordCount = calculateWordCount(updatedComponents)
                chapter.copy(
                    components = updatedComponents.sortedBy { it.order },
                    wordCount = wordCount
                )
            } else {
                chapter
            }
        }
        
        val updatedBook = book.copy(
            chapters = updatedChapters,
            updatedAt = LocalDateTime.now()
        )
        
        return bookRepository.save(updatedBook)
    }
    
    fun reorderChapterComponents(
        bookId: String,
        chapterId: String,
        componentOrders: Map<String, Int>
    ): Book? {
        val book = bookRepository.findById(bookId).orElse(null) ?: return null
        
        val updatedChapters = book.chapters.map { chapter ->
            if (chapter.id == chapterId) {
                val reorderedComponents = chapter.components.map { component ->
                    componentOrders[component.id]?.let { newOrder ->
                        when (component) {
                            is TextSection -> component.copy(order = newOrder)
                            is ImageSection -> component.copy(order = newOrder)
                            is CodeSection -> component.copy(order = newOrder)
                            is QuoteSection -> component.copy(order = newOrder)
                        }
                    } ?: component
                }.sortedBy { it.order }
                
                chapter.copy(components = reorderedComponents)
            } else {
                chapter
            }
        }
        
        return bookRepository.save(book.copy(chapters = updatedChapters))
    }
    
    private fun calculateWordCount(components: List<ChapterComponent>): Int {
        return components.sumOf { component ->
            when (component) {
                is TextSection -> component.content.split("\\s+".toRegex()).size
                is QuoteSection -> component.quote.split("\\s+".toRegex()).size
                is CodeSection -> component.description?.split("\\s+".toRegex())?.size ?: 0
                is ImageSection -> component.caption?.split("\\s+".toRegex())?.size ?: 0
            }
        }
    }
}