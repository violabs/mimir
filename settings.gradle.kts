
rootProject.name = "mimir"

val modules: List<String> = listOf(
  "core",
  "core:springJpaCore",
  "core:sharedSql",
  "first",
  "simpleKafka",
  "postgres",
  "mysql",
  "weaviate"
)

modules.forEach(::include)