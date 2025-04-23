package io.violabs.mimir.ai.kgRag.repository

import io.violabs.mimir.ai.kgRag.domain.entity.DocumentChunk
import org.springframework.data.repository.reactive.ReactiveCrudRepository

interface DocumentChunkRepository : ReactiveCrudRepository<DocumentChunk, String>