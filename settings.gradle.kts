
rootProject.name = "mimir"

val modules: Map<Int, String> = mapOf(
  0 to "core",
  1 to "first"
)

modules.values.forEach(::include)