package io.violabs.mimir.ai.kgRag.service

import io.violabs.mimir.ai.kgRag.domain.entity.Topic
import io.violabs.mimir.ai.kgRag.repository.TopicRepository
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.stereotype.Service

@Service
class TopicService(
    private val topicRepository: TopicRepository
) {

    suspend fun saveTopic(topic: Topic): Topic? {
        return topicRepository.save(topic).awaitSingleOrNull()
    }
}