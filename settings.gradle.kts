
rootProject.name = "mimir"

val modules: Map<Int, String> = mapOf(
  0 to "core",
  1 to "first",
  2 to "simpleKafka",
  3 to "kafkaConnect",
  4 to "postgres",
  5 to "springJpaCore"
)

modules.values.forEach(::include)