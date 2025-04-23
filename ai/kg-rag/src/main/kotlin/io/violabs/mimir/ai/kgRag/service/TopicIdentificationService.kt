package io.violabs.mimir.ai.kgRag.service

import io.violabs.mimir.ai.kgRag.client.NermalClient
import io.violabs.mimir.ai.kgRag.domain.NERLabel
import io.violabs.mimir.ai.kgRag.domain.entity.Topic
import io.violabs.mimir.ai.kgRag.repository.TopicTypeRepository
import org.springframework.stereotype.Service

@Service
class TopicIdentificationService(
    private val nermalClient: NermalClient,
    private val topicService: TopicService,
    private val topicTypeRepository: TopicTypeRepository
) {

    private val ignoredTopics: List<NERLabel> = listOf(
        NERLabel.PERCENT,
        NERLabel.DATE,
        NERLabel.CARDINAL,
        NERLabel.MONEY
    )

    suspend fun identifyAndSaveTopics(content: String): List<Topic> {
        val topics = extractValidTopics(content)

        return topics.mapNotNull { topicService.saveTopic(it) }
    }

    suspend fun extractValidTopics(content: String): List<Topic> {
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
                val endCharacterIndices = it.mapNotNull { r -> r.endChar }.toList()
                Topic(name, endCharacterIndices, label.name)
            }
            ?: emptyList()
    }

}