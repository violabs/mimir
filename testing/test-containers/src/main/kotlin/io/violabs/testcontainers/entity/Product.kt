package io.violabs.testcontainers.entity

import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@Table(name = "products")
data class Product(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false, unique = true)
    val sku: String,

    @Column(nullable = false)
    val name: String,

    @Column(length = 1000)
    val description: String? = null,

    @Column(nullable = false, precision = 10, scale = 2)
    val price: BigDecimal,

    @Column(nullable = false)
    val quantity: Int = 0,

    @Column(name = "category")
    val category: String? = null,

    @Column(name = "is_active")
    val isActive: Boolean = true,

    @Column(name = "created_at", nullable = false, updatable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "updated_at")
    val updatedAt: LocalDateTime = LocalDateTime.now()
) {
    @PreUpdate
    fun updateTimestamp() {
        // Note: In a data class, this won't actually modify the instance
        // For real timestamp updates, consider using @PrePersist/@PreUpdate with mutable fields
    }
}