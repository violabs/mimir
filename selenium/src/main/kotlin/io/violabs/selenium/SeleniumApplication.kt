package io.violabs.selenium

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SeleniumApplication

fun main(args: Array<String>) {
    runApplication<SeleniumApplication>(*args)
}