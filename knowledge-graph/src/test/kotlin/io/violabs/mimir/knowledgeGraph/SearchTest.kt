package io.violabs.mimir.knowledgeGraph

import io.violabs.mimir.core.testing.SimpleTestHarness
import io.violabs.mimir.knowledgeGraph.controller.KnowledgeGraphController
import io.violabs.mimir.knowledgeGraph.domain.Keyword
import io.violabs.mimir.knowledgeGraph.domain.Quote
import io.violabs.mimir.knowledgeGraph.service.KnowledgeGraphService
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class SearchTest(
    @Autowired private val knowledgeGraphController: KnowledgeGraphController,
    @Autowired private val knowledgeGraphService: KnowledgeGraphService
) : SimpleTestHarness() {

    @Test
    fun `should return the expected quotes`() = test {
        val keyword = Keyword("ethics")

        val originalQuote = Quote(
            text = "The unexamined life is not worth living.",
            keywords = listOf(keyword)
        )

        val targetQuote = runBlocking {
            knowledgeGraphService.addQuote(
                "Man is condemned to be free; because once thrown into the world," +
                    " he is responsible for everything he does.",
                listOf(
                    "sartre", "existentialism", "freedom",
                    "responsibility", "choice",
                    "french-philosophy", "absurdism"
                )
            )

            knowledgeGraphService.addQuote(
                "The unexamined life is not worth living.",
                listOf(
                    "socrates", "self-reflection", "ethics",
                    "ancient-greece", "existentialism",
                    "knowledge", "identity"
                )
            )
        }

        expect {
            listOf(
                originalQuote.copy(
                    quoteId = targetQuote?.quoteId,
                    keywords = listOf(keyword.copy(version = 0)),
                    version = 0
                )
            )
        }

        whenever {
            runBlocking {
                knowledgeGraphController.searchQuotes("ethics")
            }
        }
    }
}