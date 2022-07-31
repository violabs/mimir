package io.violabs.kafka.controller

import io.violabs.kafka.message.GodProducer
import io.violabs.kafka.message.MonsterProducer
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("send")
class TopicSendController(
  private val godProducer: GodProducer,
  private val monsterProducer: MonsterProducer
) {

  @PostMapping("gods/{name}")
  fun sendGod(@PathVariable name: String): ResponseEntity<String> = godProducer.send(name)

  @PostMapping("monsters/{name}")
  fun sendMonster(@PathVariable name: String): ResponseEntity<String> = monsterProducer.send(name)
}