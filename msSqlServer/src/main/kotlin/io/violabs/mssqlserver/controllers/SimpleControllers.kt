package io.violabs.mssqlserver.controllers

import io.violabs.mssqlserver.domain.MythicalCreature
import io.violabs.mssqlserver.services.MythicalCreatureService
import io.violabs.springjpacore.DefaultController
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("mythicalCreatures")
class MythicalCreatureController(service: MythicalCreatureService) :
    DefaultController<MythicalCreature, Long, MythicalCreatureService>(service)