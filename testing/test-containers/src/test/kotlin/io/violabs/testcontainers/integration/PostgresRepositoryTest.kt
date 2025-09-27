package io.violabs.testcontainers.integration

import io.violabs.testcontainers.entity.Product
import io.violabs.testcontainers.repository.ProductRepository
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import java.math.BigDecimal

@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
@Tag("integration")
@Tag("testcontainers")
class PostgresRepositoryTest {

    companion object {
        @Container
        @JvmStatic
        val postgresContainer = PostgreSQLContainer("postgres:15-alpine")
            .withDatabaseName("testdb")
            .withUsername("testuser")
            .withPassword("testpass")

        @JvmStatic
        @DynamicPropertySource
        fun properties(registry: DynamicPropertyRegistry) {
            registry.add("spring.datasource.url", postgresContainer::getJdbcUrl)
            registry.add("spring.datasource.username", postgresContainer::getUsername)
            registry.add("spring.datasource.password", postgresContainer::getPassword)
            registry.add("spring.datasource.driver-class-name") { "org.postgresql.Driver" }
            registry.add("spring.jpa.database-platform") { "org.hibernate.dialect.PostgreSQLDialect" }
            registry.add("spring.jpa.hibernate.ddl-auto") { "create-drop" }
            registry.add("spring.jpa.show-sql") { "true" }
        }
    }

    @Autowired
    private lateinit var productRepository: ProductRepository

    @Autowired
    private lateinit var testEntityManager: TestEntityManager

    private lateinit var testProduct: Product

    @BeforeEach
    fun setUp() {
        productRepository.deleteAll()
        testProduct = Product(
            sku = "TEST-001",
            name = "Test Product",
            description = "A test product for integration testing",
            price = BigDecimal("99.99"),
            quantity = 100,
            category = "Electronics",
            isActive = true
        )
    }

    @Test
    @Order(1)
    fun `container should be running`() {
        assertTrue(postgresContainer.isRunning)
        assertNotNull(postgresContainer.jdbcUrl)
        assertEquals("testdb", postgresContainer.databaseName)
    }

    @Test
    @Order(2)
    fun `should save and find product`() {
        // When
        val savedProduct = productRepository.save(testProduct)

        // Then
        assertNotNull(savedProduct.id)
        assertEquals(testProduct.sku, savedProduct.sku)
        assertEquals(testProduct.name, savedProduct.name)
        assertEquals(testProduct.price, savedProduct.price)

        // Test finding by ID
        val foundProduct = productRepository.findById(savedProduct.id!!)
        assertTrue(foundProduct.isPresent)
        assertEquals(savedProduct.sku, foundProduct.get().sku)
    }

    @Test
    @Order(3)
    fun `should find product by SKU`() {
        // Given
        val savedProduct = productRepository.save(testProduct)

        // When
        val foundProduct = productRepository.findBySku(testProduct.sku)

        // Then
        assertNotNull(foundProduct)
        assertEquals(savedProduct.id, foundProduct?.id)
        assertEquals(testProduct.sku, foundProduct?.sku)
    }

    @Test
    @Order(4)
    fun `should find products by name containing`() {
        // Given
        val products = listOf(
            testProduct.copy(sku = "SEARCH-001", name = "Gaming Laptop"),
            testProduct.copy(sku = "SEARCH-002", name = "Gaming Mouse"),
            testProduct.copy(sku = "SEARCH-003", name = "Office Desk")
        )
        productRepository.saveAll(products)

        // When
        val gamingProducts = productRepository.findByNameContainingIgnoreCase("Gaming")

        // Then
        assertEquals(2, gamingProducts.size)
        assertTrue(gamingProducts.all { it.name.contains("Gaming", ignoreCase = true) })
    }

    @Test
    @Order(5)
    fun `should find products by price range`() {
        // Given
        val products = listOf(
            testProduct.copy(sku = "PRICE-001", price = BigDecimal("50.00")),
            testProduct.copy(sku = "PRICE-002", price = BigDecimal("100.00")),
            testProduct.copy(sku = "PRICE-003", price = BigDecimal("150.00")),
            testProduct.copy(sku = "PRICE-004", price = BigDecimal("200.00"))
        )
        productRepository.saveAll(products)

        // When
        val affordableProducts = productRepository.findByPriceBetween(
            BigDecimal("75.00"),
            BigDecimal("175.00")
        )

        // Then
        assertEquals(2, affordableProducts.size)
        assertTrue(affordableProducts.all {
            it.price >= BigDecimal("75.00") && it.price <= BigDecimal("175.00")
        })
    }

    @Test
    @Order(6)
    fun `should check if product exists by SKU`() {
        // Given
        productRepository.save(testProduct)

        // When & Then
        assertTrue(productRepository.existsBySku(testProduct.sku))
        assertFalse(productRepository.existsBySku("NON-EXISTENT"))
    }

    @Test
    @Order(7)
    fun `should find active products only`() {
        // Given
        val products = listOf(
            testProduct.copy(sku = "ACTIVE-001", isActive = true),
            testProduct.copy(sku = "INACTIVE-001", isActive = false),
            testProduct.copy(sku = "ACTIVE-002", isActive = true)
        )
        productRepository.saveAll(products)

        // When
        val activeProducts = productRepository.findByIsActiveTrue()

        // Then
        assertEquals(2, activeProducts.size)
        assertTrue(activeProducts.all { it.isActive })
    }

    @Test
    @Order(8)
    fun `should find low stock products using custom query`() {
        // Given
        val products = listOf(
            testProduct.copy(sku = "STOCK-001", quantity = 5),
            testProduct.copy(sku = "STOCK-002", quantity = 8),
            testProduct.copy(sku = "STOCK-003", quantity = 15),
            testProduct.copy(sku = "STOCK-004", quantity = 20)
        )
        productRepository.saveAll(products)

        // When
        val lowStockProducts = productRepository.findLowStockProducts(threshold = 10)

        // Then
        assertEquals(2, lowStockProducts.size)
        assertTrue(lowStockProducts.all { it.quantity < 10 })
    }

    @AfterEach
    fun tearDown() {
        productRepository.deleteAll()
    }
}