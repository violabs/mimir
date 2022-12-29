package io.violabs.mysql.controllers

import io.violabs.mysql.domain.MythicalCreature
import io.violabs.mysql.services.MythicalCreatureService
import io.violabs.springjpacore.DefaultController
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("mythicalCreatures")
class MythicalCreatureController(service: MythicalCreatureService) :
    DefaultController<MythicalCreature, Long, MythicalCreatureService>(service)