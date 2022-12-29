package io.violabs.sharedsql.controllers

import io.violabs.sharedsql.domain.MythicalCreature
import io.violabs.sharedsql.services.MythicalCreatureService
import io.violabs.springjpacore.DefaultController
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("mythicalCreatures")
class MythicalCreatureController(service: MythicalCreatureService) :
    DefaultController<MythicalCreature, UUID, MythicalCreatureService>(service)