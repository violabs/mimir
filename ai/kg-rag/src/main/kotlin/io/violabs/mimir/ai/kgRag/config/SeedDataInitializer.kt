package io.violabs.mimir.ai.kgRag.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import io.violabs.mimir.ai.kgRag.domain.client.wikipedia.WikipediaContentResponse
import io.violabs.mimir.ai.kgRag.domain.entity.ProcessDoc
import io.violabs.mimir.ai.kgRag.service.DocumentChunkService
import io.violabs.mimir.ai.kgRag.service.QueryService
import io.violabs.mimir.core.common.Loggable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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
    private val documentChunkService: DocumentChunkService,
    private val dataService: QueryService,
    private val objectMapper: ObjectMapper,
) : Loggable {
    @Value("classpath:llm_wikipedia_content_response.json")
    private lateinit var seedText: Resource

    @Value("\${app.seed-data.limit:0}")
    private var limit: Int = 0

    protected val defaultPage by lazy {
        getPage()
    }

    protected val defaultPageContent by lazy {
        defaultPage?.extract ?: ""
    }

    protected val defaultPageTitle by lazy {
        defaultPage?.title ?: ""
    }

    protected fun getPage(): WikipediaContentResponse.Query.Page? {
        val bufferReader: BufferedReader = seedText.inputStream.bufferedReader()

        val content: WikipediaContentResponse = objectMapper.readValue(bufferReader.readText())

        return content
            .query
            ?.pages
            ?.values
            ?.firstOrNull()
            ?.also { bufferReader.close() }
    }

    @Bean
    fun seedData(): CommandLineRunner = CommandLineRunner {
        log.info("Seeding content of [WIKI]: Large_language_model")

        CoroutineScope(Dispatchers.IO).launch {
            documentChunkService.chunkAndSave(ProcessDoc("Large_language_model"), defaultPageContent)
                .also { println(it.title) }
                .chunks
                ?.forEach { println(it) }
        }

        log.info("Successfully seeded data.")
    }
}