package io.violabs.mimir.ai.kgRag.service

import io.violabs.mimir.ai.kgRag.IntegrationTestHarness
import io.violabs.mimir.ai.kgRag.domain.entity.Topic
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

class TopicServiceTest(
    @Autowired private val topicService: TopicService
) : IntegrationTestHarness() {

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

            val topics: List<Topic> = topicService.identifyAndSaveTopics(defaultPageContent)!!

            val allTopicNames: List<String> = topics.onEach { println(it) }.map { it.name }.toList()

            assert(allTopicNames.containsAll(expectedTopics)) {
                "MISSED TOPICS: " + expectedTopics.filter { it !in allTopicNames }
            }
        }
    }
}