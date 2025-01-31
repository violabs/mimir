package io.violabs.mimir.database.postgres.domain

import io.violabs.mimir.core.defaultHashCode
import io.violabs.mimir.core.springjpacore.DbEntity
import io.violabs.mimir.core.springjpacore.PropertyMap
import jakarta.persistence.*
import java.util.UUID

@Entity
@Table(indexes = [
    Index(name = "idx_mythical_creature_name", columnList = "name", unique = false)
])
class MythicalCreature(
    @Id @GeneratedValue override var id: UUID? = null,
    var name: String? = null
) : DbEntity<UUID> {
    override fun toPropertyMap(): PropertyMap = mapOf("name" to name)

    override fun toString(): String = "MythicalCreature(id: $id, name: $name)"

    override fun hashCode(): Int = id.defaultHashCode(31) + name.defaultHashCode()

    override fun equals(other: Any?): Boolean = other != null && this.hashCode() == other.hashCode()
}