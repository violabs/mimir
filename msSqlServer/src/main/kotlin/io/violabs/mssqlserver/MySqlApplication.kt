package io.violabs.mssqlserver

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MsSqlServerApplication

fun main(args: Array<String>) {
    runApplication<MsSqlServerApplication>(*args)
        .beanDefinitionNames
        .sorted()
        .forEach(::println)
}