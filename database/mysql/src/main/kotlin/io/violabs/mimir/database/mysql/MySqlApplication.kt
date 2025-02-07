package io.violabs.mimir.database.mysql

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
@EnableJpaRepositories(basePackages = ["io.violabs.mimir.core.springjpacore"])
@SpringBootApplication
class MySqlApplication

fun main(args: Array<String>) {
    runApplication<MySqlApplication>(*args)
        .beanDefinitionNames
        .sorted()
        .forEach(::println)
}
