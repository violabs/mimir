package io.violabs.mimir.ai.kgRag.service

import io.violabs.mimir.ai.kgRag.IntegrationTestHarness
import io.violabs.mimir.ai.kgRag.domain.entity.ProcessDoc
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired


class DocumentChunkServiceTest(
    @Autowired private val documentChunkService: DocumentChunkService
) : IntegrationTestHarness() {
    @Test
    fun `chunkAndSave happy path`() = runBlocking {
        val someText = defaultPageContent.substring(0, defaultPageContent.length / 8)

        val doc = ProcessDoc(defaultPageTitle)

        val result = documentChunkService.chunkAndSave(doc, someText)

        println(result.title)
        result.chunks?.forEach { println(it) }
    }
}