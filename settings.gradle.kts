
rootProject.name = "mimir"

val modules: Map<Int, String> = mapOf(
  0 to "core",
  1 to "first",
  2 to "kafka"
)

modules.values.forEach(::include)