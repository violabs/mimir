package io.violabs.mimir.ai.kgRag.repository

import io.violabs.mimir.ai.kgRag.domain.entity.ProcessDoc
import org.springframework.data.repository.reactive.ReactiveCrudRepository

interface ProcessDocRepository : ReactiveCrudRepository<ProcessDoc, String>