package io.violabs.mimir.knowledgeGraph.repository

import io.violabs.mimir.knowledgeGraph.domain.Keyword
import org.springframework.data.repository.reactive.ReactiveCrudRepository

interface KeywordRepository : ReactiveCrudRepository<Keyword, String>