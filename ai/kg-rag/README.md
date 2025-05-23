
# KG + RAG

## References

- [Apache OpenNLP](https://opennlp.sourceforge.net/models-1.5/)

## Plan

The quickest and easiest is to utilize Wikipedia to identify topics and then
store them in a knowledge graph and a vector store for the semantic search.

### Ingestion KG Pipeline
1. Read raw text
2. Sanitize raw text
3. Chunk cleaned text
4. Extract entities (NER) and determine relationships
5. Store entities and relationships in a graph database

### Ingestion Search Extraction Pipeline
1. Process the cleaned text (or chunks) to extract entities (NER) and relationships
2. Store entities and relationships in a graph database
3. Generate embeddings for text chunks
4. Store chunks and embeddings in a vector store

### Querying and Response Generation
1. Performing a vector search on the text chunks
2. Optionally, querying the knowledge graph for related information based on the query or retrieved chunks
3. Combined retrieved text chunks and KG info as context for LLM.
4. Use the LLM to generate a natural language response based on the provided context.
5. Presenting retrieved KG relationship or subgraph as response.

### Details

#### Ingestion

##### Chunking strategy

- Section as defined (wikipedia)

## Targeted relationships
Phase 2 focus on adding more complex topic relationships.

## Setup

```shell
docker compose --profile ollama --profile dev -f ./docker/docker-compose.yml up
```

Neo4j community edition
```shell
./neo4j-admin server console
```

## Diagrams

```plantuml
@startuml
actor Client

participant "Raw Text\nSource" as RawText
participant Sanitizer
participant Chunker

participant KGExtractor
participant NERExtractor
participant GraphDatabase

participant Embedder
participant VectorStore

participant QueryService
participant LLM

== Ingestion KG Extraction Pipeline ==
RawText -> Sanitizer : send raw text
Sanitizer -> Sanitizer : clean(raw text)
Sanitizer --> Chunker : cleaned text
Chunker -> Chunker : split into chunks
loop for each chunk
  Chunker --> KGExtractor : chunk
  KGExtractor -> NERExtractor : extract entities
  KGExtractor --> GraphDatabase : store doc chunks and topics
end

== Ingestion Search Extraction Pipeline ==
loop for each chunk
  KGExtractor --> Embedder : chunk
  Embedder -> Embedder : generate embedding(chunk)
  Embedder --> VectorStore : store(chunk, embedding)
end

== Query & Response Generation ==
Client -> QueryService : submit query
QueryService -> VectorStore : vectorSearch(query)
VectorStore --> QueryService : matching chunks
QueryService -> GraphDatabase : queryGraph(query or chunks)
GraphDatabase --> QueryService : related entities/relations
QueryService -> LLM : generateResponse(chunks + KG info)
LLM --> QueryService : natural‑language response
QueryService --> Client : response + KG subgraph
@enduml

```