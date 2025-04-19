
rootProject.name = "mimir"

includeModules(
  "core".subModules(
    "common",
    "springJpaCore",
    "sharedSql",
    "testing"
  ),
  "database".subModules(
    "postgres",
    "mysql"
  ),
  "first",
  "graphql".subModules(
    "async"
  ),
  "kafka".subModules("simple"),
  "knowledgeGraph",
  "logs".subModules(
    "fluentBit",
    "fluentd",
    "filebeat",
    "logstash"
  ),
  "testing".subModules(
    "selenium"
  ),
  "vector".subModules(
    "weaviate"
  )
)

class Module(private val moduleName: String) {
  override fun toString(): String = moduleName
}

fun includeModules(vararg modules: Any) {
  val ids = modules.asSequence().flatMap { it.asStrings() }.toList()

  logger.debug("including: {}", ids)
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