package io.violabs.mimir.ai.kgRag

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import io.violabs.mimir.ai.kgRag.domain.client.wikipedia.WikipediaContentResponse
import io.violabs.mimir.core.testing.SimpleTestHarness
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.core.io.Resource
import java.io.BufferedReader

@SpringBootTest
abstract class IntegrationTestHarness : SimpleTestHarness() {
    @Autowired
    protected lateinit var objectMapper: ObjectMapper

    @Value("classpath:llm_wikipedia_content_response.json")
    protected lateinit var seedText: Resource

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
    }
}