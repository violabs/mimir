package io.violabs.mimir.database.postgres

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication(scanBasePackages = ["io.violabs.mimir"])
@EntityScan("io.violabs.mimir")
@EnableJpaRepositories("io.violabs.mimir")
class PostgresApplication

fun main(args: Array<String>) {
    runApplication<PostgresApplication>(*args)
        .beanDefinitionNames
        .sorted()
        .forEach(::println)
}