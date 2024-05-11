package io.violabs.selenium

import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SeleniumConfig {
    @Bean
    fun seleniumDriver(): WebDriver {
        return ChromeDriver()
    }
}