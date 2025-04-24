package io.violabs.mimir.ai.kgRag.service

import io.violabs.mimir.ai.kgRag.IntegrationTestHarness
import io.violabs.mimir.ai.kgRag.controller.VectorStoreController
import io.violabs.mimir.ai.kgRag.domain.api.TitleRequest
import io.violabs.mimir.ai.kgRag.domain.client.ner.NERLabel
import io.violabs.mimir.ai.kgRag.domain.client.wikipedia.WikipediaDataIngestDTO
import io.violabs.mimir.ai.kgRag.domain.entity.DocumentChunk
import io.violabs.mimir.ai.kgRag.domain.entity.ProcessDoc
import io.violabs.mimir.ai.kgRag.domain.entity.Topic
import io.violabs.mimir.ai.kgRag.domain.entity.TopicType
import io.violabs.mimir.ai.kgRag.service.ingestion.IngestionPipelineService
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

class IngestionPipelineServiceTest(
    @Autowired val ingestionPipelineService: IngestionPipelineService,
    @Autowired val vectorStoreController: VectorStoreController
) : IntegrationTestHarness() {

    /**
     * This model is not very good at NER. Will have to think of a better option. Some
     * Ollama models can prove proficient, but testing required.
     */
    @Test
    fun `processIntake happy path`() {
        val title = "Hello LLM"

        val poemPart = """
        |Hello LLM, spinning neural threads,
        |Moonlight scripting poems in lunar beds.
        |Silicon rivers flow through quantum mist,
        |Coffee greets sunrise, warmly kissed.
        |JSON whispers secrets, hidden deep—
        |Dreaming databases softly sleep.
        """.trimMargin("|")

        val thoughtPart = """
        |To imagine a language is to imagine a form of life.
        |The limits of my language mean the limits of my world.
        |Language disguises thought.
        |
        |=== Sub thought ===
        |
        |If you stop dreaming, you stop living.
        """.trimMargin("|")

        val testData = """
        |Hello LLM
        |
        |== A Poem ==
        |
        |$poemPart
        |
        |== A Thought ==
        |
        |$thoughtPart
        """.trimMargin("|")

        val titleRequest = TitleRequest(title, content = testData)

        val expectedDoc = ProcessDoc(
            title, chunks = listOf(
                DocumentChunk(title, 0, title),
                DocumentChunk(
                    title, 1, poemPart, sectionName = "A Poem", topics = listOf(
                        Topic("Moonlight", TopicType(NERLabel.WORK_OF_ART.name)),
                        Topic("Silicon", TopicType(NERLabel.LOC.name))
                    )
                ),
                DocumentChunk(title, 2, thoughtPart, sectionName = "A Thought")
            )
        )

        val expected = WikipediaDataIngestDTO(title, doc = expectedDoc)

        // when
        val doc = runBlocking {
            ingestionPipelineService.processIntake(titleRequest)
        }

        Assertions.assertEquals(expected, doc) {
            "Document: ${expected.title} - ${doc.title}\n"
            "EXPECT: ${expected.doc!!.toFlattenedString()}\nACTUAL: ${expectedDoc.toFlattenedString()}"
        }

        val search = vectorStoreController.search("language", 0.8, 1)

        Assertions.assertEquals(thoughtPart, search.first().text) {
            """
            |EXPECT                             ACTUAL
            |$thoughtPart                       ${search.first().text}
            """.trimMargin("|")
        }
    }
}