# Qdrant Integration 🎯

## Docker Setup
```yaml
services:
  qdrant:
    image: qdrant/qdrant:latest
    ports:
      - "6333:6333"
      - "6334:6334"
    volumes:
      - qdrant_storage:/qdrant/storage

volumes:
  qdrant_storage:
```

## Spring Configuration 🔧
```kotlin
@Configuration
class QdrantConfig {
    @Value("\${qdrant.url}")
    lateinit var url: String

    @Bean
    fun qdrantClient() = QdrantClient(
        QdrantGrpcClient.builder()
            .host(url)
            .port(6334)
            .build()
    )
}
```

## Collection Setup 📋
```kotlin
@PostConstruct
fun initializeCollection() {
    client.createCollection(CreateCollection.newBuilder()
        .setCollectionName("documents")
        .setVectorsConfig(VectorsConfig.newBuilder()
            .setSize(384)  // BERT embedding size
            .setDistance(Distance.COSINE)
            .build())
        .build())
}
```

## Vector Operations 🔄
```kotlin
@Service
class DocumentService(
    private val client: QdrantClient,
    private val encoder: BertEncoder
) {
    fun upsert(text: String) {
        val vector = encoder.encode(text)
        client.upsert(UpsertPoints.newBuilder()
            .setCollectionName("documents")
            .addPoints(PointStruct.newBuilder()
                .setId(generateId())
                .addAllVector(vector)
                .putPayload("text", Value.newBuilder().setStringValue(text).build())
                .build())
            .build())
    }

    fun search(query: String, limit: Int = 10): List<SearchResult> {
        val queryVector = encoder.encode(query)
        return client.search(SearchPoints.newBuilder()
            .setCollectionName("documents")
            .setVector(queryVector)
            .setLimit(limit)
            .build())
    }
}
```

## Testing 🧪
```kotlin
@Test
fun `should find similar documents`() {
    // Given
    service.upsert("Kotlin coroutines guide")
    
    // When
    val results = service.search("async programming")
    
    // Then
    assertThat(results).isNotEmpty()
}
```

## Features 🌟
- Efficient vector search
- Real-time updates
- Filtering support
- Batch operations
- GRPC/REST APIs