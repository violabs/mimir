package io.violabs.mimir.ai.kgRag.service

import io.violabs.mimir.ai.kgRag.client.NermalClient
import io.violabs.mimir.ai.kgRag.domain.client.ner.NERLabel
import io.violabs.mimir.ai.kgRag.domain.entity.Topic
import io.violabs.mimir.ai.kgRag.repository.TopicRepository
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.stereotype.Service

@Service
class TopicService(
    private val nermalClient: NermalClient,
    private val topicRepository: TopicRepository
) {
    private val ignoredTopics: List<NERLabel> = listOf(
        NERLabel.PERCENT,
        NERLabel.DATE,
        NERLabel.CARDINAL,
        NERLabel.MONEY,
        NERLabel.ORDINAL
    )

    suspend fun saveTopic(topic: Topic): Topic? {
        return topicRepository.save(topic).awaitSingleOrNull()
    }

    suspend fun identifyAndSaveTopics(content: String): List<Topic>? {
        val topics = extractValidTopics(content)

        return topics?.mapNotNull { saveTopic(it) }
    }

    suspend fun extractValidTopics(content: String): List<Topic>? {
        val response = nermalClient.determineNamedEntities(content)

        return response
            ?.entities
            ?.asSequence()
            ?.filter { it.label !in ignoredTopics }
            ?.filter { "\n" !in (it.text ?: "") }
            ?.groupBy { it.text }
            ?.values
            ?.mapNotNull {
                val first = it.firstOrNull() ?: return@mapNotNull null
                val name = first.text ?: return@mapNotNull null
                val label = first.label ?: return@mapNotNull null
                Topic(name, label.name)
            }
    }
}