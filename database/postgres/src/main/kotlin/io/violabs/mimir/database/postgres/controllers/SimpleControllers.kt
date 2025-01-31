package io.violabs.mimir.database.postgres.controllers

import io.violabs.mimir.database.postgres.services.MythicalCreatureService
import io.violabs.mimir.core.springjpacore.DefaultController
import io.violabs.mimir.database.postgres.domain.MythicalCreature
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("mythical-creatures")
class MythicalCreatureController(service: MythicalCreatureService) :
    DefaultController<MythicalCreature, UUID, MythicalCreatureService>(service)