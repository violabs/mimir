
rootProject.name = "mimir"

includeModules(
  "ai".subModules("kg-rag"),
  "core".subModules(
    "common",
    "spring-jpa-core",
    "shared-sql",
    "testing"
  ),
  "database".subModules(
    "postgres",
    "mysql",
    "mongo"
  ),
  "graphql".subModules(
    "async"
  ),
  "kafka".subModules(
    "simple",
    "schema-registry-avro"
  ),
  "knowledge-graph",
  "logs".subModules(
    "fluent-bit",
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