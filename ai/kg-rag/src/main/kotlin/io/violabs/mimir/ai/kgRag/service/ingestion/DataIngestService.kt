package io.violabs.mimir.ai.kgRag.service.ingestion

import io.violabs.mimir.ai.kgRag.domain.DataIngestDocument
import io.violabs.mimir.ai.kgRag.domain.api.TitleRequest

interface DataIngestService<T : TitleRequest, R : DataIngestDocument> {
    suspend fun gatherAndSave(request: T): R
    suspend fun getRawContent(request: T): R
}