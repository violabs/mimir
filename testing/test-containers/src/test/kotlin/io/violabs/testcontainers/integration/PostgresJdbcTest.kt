package io.violabs.testcontainers.integration

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import java.sql.DriverManager

@Testcontainers
class PostgresJdbcTest {

    companion object {
        @Container
        @JvmStatic
        val postgresContainer = PostgreSQLContainer("postgres:15-alpine")
            .withDatabaseName("testdb")
            .withUsername("testuser")
            .withPassword("testpass")
    }

    @Test
    fun `container should be running`() {
        assertTrue(postgresContainer.isRunning)
        assertNotNull(postgresContainer.jdbcUrl)
        assertEquals("testdb", postgresContainer.databaseName)
    }

    @Test
    fun `should connect to database using JDBC`() {
        val connection = DriverManager.getConnection(
            postgresContainer.jdbcUrl,
            postgresContainer.username,
            postgresContainer.password
        )

        assertTrue(connection.isValid(1))

        // Create a test table
        val statement = connection.createStatement()
        statement.execute("""
            CREATE TABLE IF NOT EXISTS test_table (
                id SERIAL PRIMARY KEY,
                name VARCHAR(100)
            )
        """)

        // Insert test data
        statement.execute("INSERT INTO test_table (name) VALUES ('Test Data')")

        // Query the data
        val resultSet = statement.executeQuery("SELECT * FROM test_table")
        assertTrue(resultSet.next())
        assertEquals("Test Data", resultSet.getString("name"))

        // Clean up
        statement.close()
        connection.close()
    }

    @Test
    fun `should execute PostgreSQL specific queries`() {
        val connection = DriverManager.getConnection(
            postgresContainer.jdbcUrl,
            postgresContainer.username,
            postgresContainer.password
        )

        val statement = connection.createStatement()
        val resultSet = statement.executeQuery("SELECT version()")

        assertTrue(resultSet.next())
        val version = resultSet.getString(1)
        assertTrue(version.contains("PostgreSQL"))
        assertTrue(version.contains("15"))

        statement.close()
        connection.close()
    }
}