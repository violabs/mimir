package io.violabs.mimir.graphql.async.repository

import io.violabs.mimir.graphql.async.domain.MythicalCreature
import org.springframework.data.repository.CrudRepository
import java.util.UUID

interface MythicalCreatureRepository : CrudRepository<MythicalCreature, UUID>