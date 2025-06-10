package io.violabs.mimir.mongo.repo

import io.violabs.mimir.mongo.domain.Book
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface BookRepository : MongoRepository<Book, String> {
    
    fun findByAuthor(author: String): List<Book>
    
    fun findByTitleContainingIgnoreCase(title: String): List<Book>
    
    @Query("{'chapters.title': {\$regex: ?0, \$options: 'i'}}")
    fun findByChapterTitleContaining(chapterTitle: String): List<Book>
    
    @Query("{'metadata.genre': ?0}")
    fun findByGenre(genre: String): List<Book>
    
    @Query("{'isbn': ?0}")
    fun findByIsbn(isbn: String): Book?
}