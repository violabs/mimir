package io.violabs.postgres.controllers

import io.violabs.postgres.domain.MythicalCreature
import io.violabs.postgres.services.MythicalCreatureService
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("mythicalCreatures")
class MythicalCreatureController(service: MythicalCreatureService) :
    DefaultController<MythicalCreature, UUID, MythicalCreatureService>(service)