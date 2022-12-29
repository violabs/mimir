package io.violabs.postgres.services

import io.violabs.sharedsql.services.MythicalCreatureRepository
import io.violabs.sharedsql.services.MythicalCreatureService
import io.violabs.sharedsql.test.MythicalCreatureServiceIntegrationTestSetup
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class MythicalCreatureServiceIntegrationTest(
    @Autowired private val mythicalCreatureRepository: MythicalCreatureRepository,
    @Autowired private val mythicalCreatureService: MythicalCreatureService
) : MythicalCreatureServiceIntegrationTestSetup(
    mythicalCreatureRepository,
    mythicalCreatureService
)