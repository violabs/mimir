package io.violabs.mimir.ibmmq.producer

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = ["io.violabs.mimir"])
class IbmMqApplication

fun main(args: Array<String>) {
    runApplication<IbmMqApplication>(*args)
}
