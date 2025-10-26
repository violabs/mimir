package io.violabs.mimir.ibmmq.producer

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = ["io.violabs.mimir"])
class IbmMqProducerApplication

fun main(args: Array<String>) {
    runApplication<IbmMqProducerApplication>(*args)
}
