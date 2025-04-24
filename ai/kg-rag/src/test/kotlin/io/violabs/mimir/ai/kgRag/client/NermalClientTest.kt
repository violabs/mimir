package io.violabs.mimir.ai.kgRag.client

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import io.violabs.mimir.ai.kgRag.domain.client.wikipedia.WikipediaContentResponse
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.core.io.Resource
import java.io.BufferedReader

@SpringBootTest
class NermalClientTest(
    @Autowired private val nermalClient: NermalClient,
    @Autowired private val objectMapper: ObjectMapper
) {
    @Value("classpath:llm_wikipedia_content_response.json")
    private lateinit var seedText: Resource

    @Test
    fun testNERProcessing() {
        runBlocking {
            val bufferReader: BufferedReader = seedText.inputStream.bufferedReader()

            val content: WikipediaContentResponse = objectMapper.readValue(bufferReader.readText())

            nermalClient.determineNamedEntities(content.query!!.pages!!.values.first().extract!!)
                ?.entities
                ?.forEach { println(it) }
        }
    }
}