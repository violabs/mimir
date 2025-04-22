package io.violabs.mimir.ai.kgRag.service

import io.violabs.mimir.ai.kgRag.domain.DataIngestItem
import io.violabs.mimir.ai.kgRag.domain.DataIngestTitleRequest

interface DataIngestService<T : DataIngestTitleRequest, R : DataIngestItem> {
    suspend fun getContentWithTopicsIfAvailable(request: T): R
}