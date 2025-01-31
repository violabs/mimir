package io.violabs.mimir.kafka.simple.controller

import io.violabs.mimir.kafka.simple.message.GodProducer
import io.violabs.mimir.kafka.simple.message.MonsterProducer
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