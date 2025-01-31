
rootProject.name = "mimir"

includeModules(
  "core".subModules(
    "common",
    "springJpaCore",
    "sharedSql"
  ),
  "first",
  "kafka".subModules("simple"),
  "database".subModules(
    "postgres",
    "mysql"
  ),
  "vector".subModules(
    "weaviate"
  ),
  "testing".subModules(
    "selenium"
  ),
  "logs".subModules(
    "fluentBit",
    "fluentd",
    "filebeat",
    "logstash"
  )
)

class Module(private val moduleName: String) {
  override fun toString(): String = moduleName
}

fun includeModules(vararg modules: Any) {
  val ids = modules.asSequence().flatMap { it.asStrings() }.toList()

  logger.lifecycle("including: $ids")
  include(ids)
}

fun Any.asStrings(): Sequence<String> = when (this) {
  is String -> sequenceOf(this)
  is List<*> -> this.asSequence().map { it.toString() }
  else -> sequenceOf(toString())
}

fun String.subModules(vararg subModulesIds: String): List<Module> {
  val subModules = subModulesIds.map { Module("$this:$it") }

  return listOf(Module(this)) + subModules
}