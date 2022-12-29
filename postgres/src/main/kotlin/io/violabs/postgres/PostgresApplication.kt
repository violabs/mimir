package io.violabs.postgres

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication
@EntityScan("io.violabs.sharedsql.domain")
@ComponentScan("io.violabs.sharedsql.services")
@EnableJpaRepositories("io.violabs.sharedsql.services")
class PostgresApplication

fun main(args: Array<String>) {
    runApplication<PostgresApplication>(*args)
        .beanDefinitionNames
        .sorted()
        .forEach(::println)
}