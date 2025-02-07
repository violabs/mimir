package io.violabs.mimir.database.postgres

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication
@EnableJpaRepositories(basePackages = ["io.violabs.mimir.core.springJpaCore"])
@ComponentScan(basePackages = ["io.violabs.mimir.core.springJpaCore", "io.violabs.mimir.database.postgres"])
class PostgresApplication

fun main(args: Array<String>) {
    runApplication<PostgresApplication>(*args)
        .beanDefinitionNames
        .sorted()
        .forEach(::println)
}