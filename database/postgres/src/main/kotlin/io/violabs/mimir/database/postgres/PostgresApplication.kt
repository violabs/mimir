package io.violabs.mimir.database.postgres

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication
@EnableJpaRepositories(basePackages = ["io.violabs.mimir.core.springJpaCore"])
class PostgresApplication

fun main(args: Array<String>) {
    runApplication<PostgresApplication>(*args)
        .beanDefinitionNames
        .sorted()
        .forEach(::println)
}