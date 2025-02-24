# GraphQL Integration Guide

This guide covers the GraphQL implementations available in the Mimir project, focusing on both synchronous and asynchronous patterns with Spring Boot and Kotlin.

## Modules Overview

- **Async Module**: Implementation of asynchronous GraphQL operations using Kotlin coroutines
- Future modules planned:
  - Subscription handling
  - Batching patterns
  - Schema federation

## Quick Links

- [Async Implementation Guide](async.md)
- [Schema Design Patterns](schema-design.md)
- [Performance Best Practices](performance.md)
- [Security Considerations](security.md)

## Architecture Overview

The GraphQL implementation in Mimir follows these key principles:

1. **Asynchronous First**: Designed for non-blocking operations
2. **Type Safety**: Leveraging Kotlin's type system
3. **Schema Modularity**: Breaking schemas into maintainable pieces
4. **Performance Optimization**: Implementing DataLoader patterns
5. **Security Integration**: Built-in security best practices

## Getting Started

1. Choose your module (e.g., async)
2. Follow module-specific setup guide
3. Review schema design guidelines
4. Implement resolvers and types
5. Configure security and performance settings