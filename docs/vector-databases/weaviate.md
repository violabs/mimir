# Weaviate Integration 🌐

## Docker Setup
```yaml
services:
  weaviate:
    image: semitechnologies/weaviate:latest
    ports:
      - "8080:8080"
    environment:
      QUERY_DEFAULTS_LIMIT: 25
      AUTHENTICATION_ANONYMOUS_ACCESS_ENABLED: 'true'
      DEFAULT_VECTORIZER_MODULE: 'text2vec-transformers'
      ENABLE_MODULES: 'text2vec-transformers'
```

## Spring Configuration 🔧
```kotlin
@Configuration
class WeaviateConfig {
    @Value("\${weaviate.url}")
    lateinit var url: String

    @Bean
    fun weaviateClient() = WeaviateClient(url)
}
```

## Schema Definition 📋
```kotlin
data class Document(
    val content: String,
    val metadata: Map<String, Any> = emptyMap()
)

fun createSchema() {
    weaviateClient.schema()
        .classCreator()
        .withClass(WeaviateClass.builder()
            .className("Document")
            .vectorizer("text2vec-transformers")
            .build())
        .run()
}
```

## CRUD Operations 🔄
```kotlin
@Service
class DocumentService(private val client: WeaviateClient) {
    fun create(doc: Document) = client.data()
        .creator()
        .withClassName("Document")
        .withProperties(doc.toMap())
        .run()

    fun search(query: String, limit: Int = 10) = client.graphQL()
        .get()
        .withClassName("Document")
        .withFields("content")
        .withNearText(NearTextArgument.builder()
            .concepts(listOf(query))
            .build())
        .withLimit(limit)
        .run()
}
```

## Testing 🧪
```kotlin
@Test
fun `should perform semantic search`() {
    // Given
    service.create(Document("Kotlin programming basics"))
    
    // When
    val results = service.search("programming tutorial")
    
    // Then
    assertThat(results).isNotEmpty()
}
```