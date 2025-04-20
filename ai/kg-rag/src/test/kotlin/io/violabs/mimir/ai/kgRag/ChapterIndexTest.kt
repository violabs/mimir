package io.violabs.mimir.ai.kgRag

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.core.io.Resource

@SpringBootTest
class ChapterIndexTest {
    @Value("classpath:relativity_the_special_and_general_theory.txt")
    private lateinit var seedText: Resource

    @Test
    fun testChapterIndex() {
        println(seedText.exists())
    }
}