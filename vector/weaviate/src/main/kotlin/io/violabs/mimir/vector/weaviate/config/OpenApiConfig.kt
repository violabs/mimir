package io.violabs.mimir.vector.weaviate.config

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.info.Contact
import io.swagger.v3.oas.models.info.License
import io.swagger.v3.oas.models.servers.Server
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OpenApiConfig(
    @Value("\${spring.application.name}") private val applicationName: String
) {
    
    @Bean
    fun openAPI(): OpenAPI = OpenAPI()
        .addServersItem(
            Server()
                .url("http://localhost:8082")
                .description("Local Development Server")
        )
        .info(
            Info()
                .title("$applicationName API")
                .description("""
                    API for managing vector embeddings using Weaviate.
                    This service provides endpoints for storing, retrieving, and searching vector embeddings.
                """.trimIndent())
                .version("1.0.0")
                .contact(
                    Contact()
                        .name("Violabs")
                        .url("https://github.com/violabs")
                )
                .license(
                    License()
                        .name("MIT License")
                        .url("https://opensource.org/licenses/MIT")
                )
        )
}