package io.violabs.testcontainers.service

import io.violabs.testcontainers.entity.Product
import io.violabs.testcontainers.repository.ProductRepository
import mu.two.KotlinLogging
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal

private val logger = KotlinLogging.logger {}

@Service
@Transactional
class ProductService(
    private val productRepository: ProductRepository
) {

    fun createProduct(product: Product): Product {
        logger.info { "Creating new product with SKU: ${product.sku}" }

        if (productRepository.existsBySku(product.sku)) {
            throw IllegalArgumentException("Product with SKU ${product.sku} already exists")
        }

        return productRepository.save(product).also {
            logger.debug { "Product created successfully with ID: ${it.id}" }
        }
    }

    fun updateProduct(id: Long, updatedProduct: Product): Product {
        logger.info { "Updating product with ID: $id" }

        val existingProduct = getProductById(id)

        val productToSave = existingProduct.copy(
            name = updatedProduct.name,
            description = updatedProduct.description,
            price = updatedProduct.price,
            quantity = updatedProduct.quantity,
            category = updatedProduct.category,
            isActive = updatedProduct.isActive
        )

        return productRepository.save(productToSave).also {
            logger.debug { "Product updated successfully with ID: ${it.id}" }
        }
    }

    @Transactional(readOnly = true)
    fun getProductById(id: Long): Product {
        logger.debug { "Fetching product with ID: $id" }
        return productRepository.findById(id)
            .orElseThrow { NoSuchElementException("Product not found with ID: $id") }
    }

    @Transactional(readOnly = true)
    fun getProductBySku(sku: String): Product? {
        logger.debug { "Fetching product with SKU: $sku" }
        return productRepository.findBySku(sku)
    }

    @Transactional(readOnly = true)
    fun getAllProducts(pageable: Pageable): Page<Product> {
        logger.debug { "Fetching all products with pagination" }
        return productRepository.findAll(pageable)
    }

    @Transactional(readOnly = true)
    fun getActiveProducts(): List<Product> {
        logger.debug { "Fetching all active products" }
        return productRepository.findByIsActiveTrue()
    }

    @Transactional(readOnly = true)
    fun searchProductsByName(name: String): List<Product> {
        logger.debug { "Searching products with name containing: $name" }
        return productRepository.findByNameContainingIgnoreCase(name)
    }

    @Transactional(readOnly = true)
    fun getProductsByCategory(category: String): List<Product> {
        logger.debug { "Fetching products in category: $category" }
        return productRepository.findByCategory(category)
    }

    @Transactional(readOnly = true)
    fun getProductsByPriceRange(minPrice: BigDecimal, maxPrice: BigDecimal): List<Product> {
        logger.debug { "Fetching products with price between $minPrice and $maxPrice" }

        if (minPrice > maxPrice) {
            throw IllegalArgumentException("Minimum price cannot be greater than maximum price")
        }

        return productRepository.findByPriceBetween(minPrice, maxPrice)
    }

    @Transactional(readOnly = true)
    fun getLowStockProducts(threshold: Int = 10): List<Product> {
        logger.info { "Fetching products with stock below threshold: $threshold" }
        return productRepository.findLowStockProducts(threshold)
    }

    @Transactional(readOnly = true)
    fun getProductCountByCategory(): Map<String, Long> {
        logger.debug { "Getting product count by category" }
        return productRepository.countProductsByCategory()
            .associate {
                (it[0] as? String ?: "Unknown") to (it[1] as? Long ?: 0L)
            }
    }

    fun updateStock(productId: Long, quantity: Int): Product {
        logger.info { "Updating stock for product ID: $productId, quantity change: $quantity" }

        val product = getProductById(productId)
        val newQuantity = product.quantity + quantity

        if (newQuantity < 0) {
            throw IllegalArgumentException("Insufficient stock. Current: ${product.quantity}, Requested: $quantity")
        }

        val updatedProduct = product.copy(quantity = newQuantity)
        return productRepository.save(updatedProduct).also {
            logger.debug { "Stock updated successfully. New quantity: ${it.quantity}" }
        }
    }

    fun deactivateProduct(id: Long): Product {
        logger.info { "Deactivating product with ID: $id" }

        val product = getProductById(id)
        val deactivatedProduct = product.copy(isActive = false)

        return productRepository.save(deactivatedProduct).also {
            logger.debug { "Product deactivated successfully with ID: ${it.id}" }
        }
    }

    fun deleteProduct(id: Long) {
        logger.info { "Deleting product with ID: $id" }

        if (!productRepository.existsById(id)) {
            throw NoSuchElementException("Product not found with ID: $id")
        }

        productRepository.deleteById(id)
        logger.debug { "Product deleted successfully with ID: $id" }
    }

    fun cleanupInactiveProducts(): Long {
        logger.info { "Cleaning up inactive products" }

        val deletedCount = productRepository.deleteByIsActiveFalse()
        logger.info { "Deleted $deletedCount inactive products" }

        return deletedCount
    }

    @Transactional(readOnly = true)
    fun getRecentAffordableProducts(maxPrice: BigDecimal, limit: Int = 10): List<Product> {
        logger.debug { "Fetching recent products under price: $maxPrice, limit: $limit" }
        return productRepository.findRecentProductsUnderPrice(maxPrice, limit)
    }
}