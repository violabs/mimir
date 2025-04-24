package io.violabs.mimir.ai.kgRag.service

import io.violabs.mimir.ai.kgRag.IntegrationTestHarness
import io.violabs.mimir.ai.kgRag.domain.client.ner.NERLabel
import io.violabs.mimir.ai.kgRag.domain.entity.DocumentChunk
import io.violabs.mimir.ai.kgRag.domain.entity.ProcessDoc
import io.violabs.mimir.ai.kgRag.domain.entity.Topic
import io.violabs.mimir.ai.kgRag.domain.entity.TopicType
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired


class DocumentChunkServiceTest(
    @Autowired private val documentChunkService: DocumentChunkService
) : IntegrationTestHarness() {
    private val testDoc = """
        This is before a header.
        
        == Header ==
        
        content of the first header with LLM.
        
        == Second Header ==
        
        === Sub Section ===
        
        content of second header. 2024 New York
    """.trimIndent()

    @Test
    fun `chunkAndSave happy path`() = test {
        expect {
            ProcessDoc(
                "Test Doc",
                chunks = listOf(
                    DocumentChunk(
                        docName = "Test Doc",
                        0,
                        "This is before a header."
                    ),
                    DocumentChunk(
                        docName = "Test Doc",
                        1,
                        content = "content of the first header with LLM.",
                        sectionName = "Header",
                        topics = listOf(
                            Topic("LLM", TopicType(NERLabel.ORG.name))
                        )
                    ),
                    DocumentChunk(
                        docName = "Test Doc",
                        2,
                        content = "=== Sub Section ===\n\ncontent of second header. 2024 New York",
                        sectionName = "Second Header",
                        topics = listOf(
                            Topic("New York", TopicType(NERLabel.GPE.name))
                        )
                    )
                )
            )
        }

        whenever {
            runBlocking {
                val doc = ProcessDoc("Test Doc")

                documentChunkService.chunkAndSave(doc, testDoc)
            }
        }
    }
}