package io.violabs.mimir.vector.weaviate.config

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.info.Contact
import io.swagger.v3.oas.models.info.License
import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.security.SecurityScheme
import io.swagger.v3.oas.models.servers.Server
import io.swagger.v3.oas.models.tags.Tag
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.beans.factory.annotation.Value

@Configuration
class OpenApiConfig(
    @Value("\${spring.application.name}")
    private val applicationName: String
) {
    @Bean
    fun openAPI(): OpenAPI = OpenAPI()
        .info(apiInfo())
        .servers(listOf(
            Server()
                .url("/")
                .description("Local Environment")
        ))
        .components(
            Components()
                .addSecuritySchemes(
                    "bearer-jwt",
                    SecurityScheme()
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT")
                        .description("JWT token authentication")
                )
        )
        .addTagsItem(
            Tag()
                .name("vector-operations")
                .description("Vector store operations for embeddings and searches")
        )
        .addTagsItem(
            Tag()
                .name("health")
                .description("Health check endpoints")
        )

    private fun apiInfo() = Info()
        .title("Mimir $applicationName API")
        .description("""
            Vector store API using Weaviate for storing and retrieving embeddings.
            
            ## Features
            - Store vector embeddings
            - Search by vector similarity
            - Batch operations support
            - Integration with Ollama for embeddings generation
            
            ## Getting Started
            1. Ensure Weaviate is running
            2. Configure Ollama connection
            3. Use the API endpoints to store and search vectors
            
            For more information, please refer to the project documentation.
        """.trimIndent())
        .version("1.0.0")
        .contact(
            Contact()
                .name("Violabs")
                .url("https://github.com/violabs/mimir")
        )
        .license(
            License()
                .name("MIT License")
                .url("https://opensource.org/licenses/MIT")
        )
        .termsOfService("https://github.com/violabs/mimir/blob/main/LICENSE")
}