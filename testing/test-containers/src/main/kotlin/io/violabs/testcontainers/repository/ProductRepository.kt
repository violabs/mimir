package io.violabs.testcontainers.repository

import io.violabs.testcontainers.entity.Product
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.math.BigDecimal

@Repository
interface ProductRepository : JpaRepository<Product, Long> {

    fun findBySku(sku: String): Product?

    fun findByNameContainingIgnoreCase(name: String): List<Product>

    fun findByCategory(category: String): List<Product>

    fun findByIsActiveTrue(): List<Product>

    fun findByPriceBetween(minPrice: BigDecimal, maxPrice: BigDecimal): List<Product>

    @Query("SELECT p FROM Product p WHERE p.quantity < :threshold AND p.isActive = true")
    fun findLowStockProducts(@Param("threshold") threshold: Int): List<Product>

    @Query("SELECT p.category, COUNT(p) FROM Product p GROUP BY p.category")
    fun countProductsByCategory(): List<Array<Any>>

    @Query(
        value = "SELECT * FROM products WHERE price <= :maxPrice ORDER BY created_at DESC LIMIT :limit",
        nativeQuery = true
    )
    fun findRecentProductsUnderPrice(
        @Param("maxPrice") maxPrice: BigDecimal,
        @Param("limit") limit: Int
    ): List<Product>

    fun existsBySku(sku: String): Boolean

    fun deleteByIsActiveFalse(): Long
}