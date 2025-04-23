package io.violabs.mimir.ai.kgRag.config

import io.violabs.mimir.ai.kgRag.domain.api.AddTextBlockRequest
import io.violabs.mimir.ai.kgRag.repository.VectorStoreDAO
import io.violabs.mimir.ai.kgRag.service.TopicService
import io.violabs.mimir.core.common.Loggable
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.Resource
import java.io.BufferedReader

@Configuration
@ConditionalOnProperty(value = ["app.seed-data.enabled"], havingValue = "true")
class SeedDataInitializer(
    private val topicService: TopicService,
    private val dataService: VectorStoreDAO
) : Loggable  {

    @Value("classpath:llm_wikipedia_content_response.json")
    private lateinit var seedText: Resource

    @Value("\${app.seed-data.limit:0}")
    private var limit: Int = 0

    @Bean
    fun seedData(): CommandLineRunner = CommandLineRunner {
        log.info("Seeding content of [WIKI]: Large_language_model")
        val bufferedReader = seedText
            .inputStream
            .bufferedReader()

        var lazyContent = bufferedReader.paragraphSequence()

        if (limit > 0) {
            lazyContent = lazyContent.take(limit)
        }

        val content = lazyContent
            .onEachIndexed { i, p -> log.debug("$i: $p") }
            .toList()

        bufferedReader.close()

        val request = AddTextBlockRequest(
            content,
            title = "Large_language_model"
        )

        dataService.addContent(request)

        log.info("Successfully seeded data.")
    }

    fun BufferedReader.paragraphSequence(): Sequence<String> = sequence {
        val sb = StringBuilder()
        for (line in lineSequence()) {
            if (line.isBlank()) {
                if (sb.isNotEmpty()) {
                    yield(sb.toString().trim())
                    sb.clear()
                }
            } else {
                sb.append(line).append("\n")
            }
        }
        if (sb.isNotEmpty()) yield(sb.toString().trim())
    }
}