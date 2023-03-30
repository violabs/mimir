package io.violabs.milvus

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MilvusApplication

fun main(args: Array<String>) {
    runApplication<MilvusApplication>(*args)
        .beanDefinitionNames
        .sorted()
        .forEach(::println)
}