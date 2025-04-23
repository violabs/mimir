package io.violabs.mimir.ai.kgRag

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import io.violabs.mimir.ai.kgRag.domain.WikipediaContentResponse
import io.violabs.mimir.ai.kgRag.domain.entity.Topic
import io.violabs.mimir.ai.kgRag.service.TopicIdentificationService
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.core.io.Resource
import java.io.BufferedReader

@SpringBootTest
class TopicIdentificationServiceTest(
    @Autowired private val objectMapper: ObjectMapper,
    @Autowired private val topicIdentificationService: TopicIdentificationService
) {
    @Value("classpath:llm_wikipedia_content_response.json")
    private lateinit var seedText: Resource

    @Test
    fun testTopicIdentificationService() {
        runBlocking {
            val expectedTopics: List<String> = listOf(
                "AI",
                "Attention Is All You Need",
                "BERT",
                "Claude",
                "DeepSeek R1",
                "Gemini",
                "GPT",
                "LLM",
                "Mistral 7B",
                "Mixtral",
                "MMLU",
                "MoE",
                "Neural Machine Translation",
                "PaLM",
                "Reflexion",
                "Retrieval Augmented Generation",
                "RLHF",
                "Transcoders",
                "Transformer"
            )
            val bufferReader: BufferedReader = seedText.inputStream.bufferedReader()

            val content: WikipediaContentResponse = objectMapper.readValue(bufferReader.readText())

            val page = content
                .query
                ?.pages
                ?.values
                ?.firstOrNull()

            val topics: List<Topic> = topicIdentificationService.identifyAndSaveTopics(page?.extract ?: "")

            val allTopicNames: List<String> = topics.onEach { println(it) }.map { it.name }.toList()

            assert(allTopicNames.containsAll(expectedTopics)) {
                "MISSED TOPICS: " + expectedTopics.filter { it !in allTopicNames }
            }
        }
    }
}