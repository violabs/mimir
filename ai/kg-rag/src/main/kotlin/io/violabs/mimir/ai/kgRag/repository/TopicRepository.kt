package io.violabs.mimir.ai.kgRag.repository

import io.violabs.mimir.ai.kgRag.domain.entity.Topic
import org.springframework.data.repository.reactive.ReactiveCrudRepository

interface TopicRepository : ReactiveCrudRepository<Topic, String>