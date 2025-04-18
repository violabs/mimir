package io.violabs.mimir.vector.weaviate

import io.violabs.mimir.core.testing.assertExpected
import io.violabs.mimir.vector.weaviate.controller.VectorStoreController
import io.violabs.mimir.vector.weaviate.domain.request.AddSentenceRequest
import io.violabs.mimir.vector.weaviate.service.DataService
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class SearchTest(
    @Autowired private val vectorStoreController: VectorStoreController,
    @Autowired private val dataService: DataService
) {

    @Test
    fun `search should return relevant information`() {
        //given
        val target1 = "Underneath all reason lies delirium and drift."
        val target2 = "What cannot be said above all must not be silenced but written."

        dataService.addContent(
            AddSentenceRequest(
                listOf(
                    target1,
                    target2,
                    "To create is to live twice."
                ),
                "France",
                1960
            )
        )

        dataService.addContent(
            AddSentenceRequest(
                listOf("Knowledge about a thing is not the thing itself."),
                "US",
                1902
            )
        )

        //when
        val response = vectorStoreController.search(
            "all", 0.70, 10, true
        ).sortedBy { it.text }

        println(response)

        assertExpected(response.size, 2)
        assertExpected(response.first().text, target1)
        assertExpected(response.last().text, target2)
    }
}