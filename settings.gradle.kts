
rootProject.name = "mimir"

val modules: List<String> = listOf(
  "core",
  "core:springJpaCore",
  "core:sharedSql",
  "first",
  "simpleKafka",
  "kafkaConnect",
  "postgres",
  "mysql"
)

modules.forEach(::include)