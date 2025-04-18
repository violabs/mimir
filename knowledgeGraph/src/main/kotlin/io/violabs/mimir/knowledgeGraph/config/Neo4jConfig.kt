package io.violabs.mimir.knowledgeGraph.config

import org.neo4j.ogm.session.SessionFactory
import org.springframework.beans.factory.annotation.Value
import org.neo4j.ogm.config.Configuration as Neo4jConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class Neo4jConfig(
    @Value("\${spring.neo4j.uri}")
    val uri: String,
    @Value("\${spring.neo4j.authentication.username}")
    val username: String,
    @Value("\${spring.neo4j.authentication.password}")
    val password: String
) {

    @Bean
    fun neo4jConfiguration(): Neo4jConfiguration {
        return Neo4jConfiguration.Builder()
            .uri(uri)
            .credentials(username, password)
            .build()
    }

    @Bean
    fun sessionFactory(neo4jConfiguration: Neo4jConfiguration): SessionFactory {
        return SessionFactory(neo4jConfiguration, "io.violabs.mimir.knowledgeGraph.domain")
    }
}