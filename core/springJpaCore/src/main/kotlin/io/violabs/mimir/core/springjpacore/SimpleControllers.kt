package io.violabs.mimir.core.springjpacore

import io.violabs.mimir.core.springjpacore.domain.MythicalCreature
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("mythical-creatures")
class MythicalCreatureController(service: MythicalCreatureService) :
    DefaultController<MythicalCreature, UUID, MythicalCreatureService>(service)