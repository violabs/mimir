package io.violabs.mimir.kafka.simple

import io.violabs.mimir.kafka.simple.domain.God
import io.violabs.mimir.kafka.simple.message.GodListener
import io.violabs.mimir.kafka.simple.message.GodProducer
import io.violabs.mimir.kafka.simple.message.Handler
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.kafka.test.context.EmbeddedKafka
import org.springframework.test.annotation.DirtiesContext
import java.util.concurrent.TimeUnit

@SpringBootTest
@DirtiesContext
@EmbeddedKafka(
  partitions = 1,
  brokerProperties = ["listeners=PLAINTEXT://localhost:9092", "port=9092"]
)
class EmbeddedKafkaIntegrationTest {
  @Autowired
  private lateinit var godProducer: GodProducer

  @Autowired
  private lateinit var godListener: GodListener

  @MockBean
  private lateinit var handler: Handler

  @Test
  fun sendReceivesMessage() {
    //given
    val name = "Freya"

    val expected = God(name)

    //when
    godProducer.send(name)

    //then
    val processed: Boolean = godListener.latch.await(10, TimeUnit.SECONDS)

    Assertions.assertTrue(processed)
    Mockito.verify(handler, Mockito.times(1)).handleBeing(expected)
    Mockito.verifyNoMoreInteractions(handler)
  }
}