package io.violabs.mimir.database.mysql.controllers

import io.violabs.mimir.core.springjpacore.DefaultController
import io.violabs.mimir.database.mysql.domain.MythicalCreature
import io.violabs.mimir.database.mysql.services.MythicalCreatureService
import org.springframework.web.bind.annotation.*
import java.util.UUID

@RestController
@RequestMapping("mythical-creatures")
class MythicalCreatureController(service: MythicalCreatureService) :
    DefaultController<MythicalCreature, UUID, MythicalCreatureService>(service)