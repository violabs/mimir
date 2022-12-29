package io.violabs.postgres.domain

import io.violabs.core.defaultHashCode
import io.violabs.springjpacore.DbEntity
import io.violabs.springjpacore.PropertyMap
import java.util.UUID
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Index
import javax.persistence.Table

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