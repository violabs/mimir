package io.violabs.mimir.testing.selenium

import org.openqa.selenium.WebDriver
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("test")
class TestController(private val seleniumDriver: WebDriver) {

    @GetMapping
    fun basicTest(): String {
        seleniumDriver.get("https://selenium.dev")
        return seleniumDriver.title
    }
}