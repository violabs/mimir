package io.violabs.mimir.ai.kgRag.config

import org.neo4j.cypherdsl.core.renderer.Dialect
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.neo4j.cypherdsl.core.renderer.Configuration as CypherConfig

@Configuration
class Neo4jConfig {

    @Bean
    fun cypherDslConfiguration(): CypherConfig {
        return CypherConfig.newConfig().withDialect(Dialect.NEO4J_5).build()
    }
}