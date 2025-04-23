package io.violabs.mimir.ai.kgRag.repository

import io.violabs.mimir.ai.kgRag.domain.entity.TopicType
import org.springframework.data.repository.reactive.ReactiveCrudRepository

interface TopicTypeRepository : ReactiveCrudRepository<TopicType, String>