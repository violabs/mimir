# GraphQL Schema Design Patterns

This guide covers best practices and patterns for designing GraphQL schemas in the Mimir project.

## Type Definitions

### Basic Types

```graphql
type User {
    id: ID!
    username: String!
    email: String!
    profile: Profile
    posts: [Post!]!
}

type Profile {
    bio: String
    avatar: String
    socialLinks: [SocialLink!]
}

type Post {
    id: ID!
    title: String!
    content: String!
    author: User!
}
```

### Input Types

```graphql
input CreateUserInput {
    username: String!
    email: String!
    profileData: ProfileInput
}

input ProfileInput {
    bio: String
    avatar: String
}
```

## Query Design

### Pagination

```graphql
type Query {
    users(first: Int, after: String): UserConnection!
    posts(first: Int, after: String): PostConnection!
}

type UserConnection {
    edges: [UserEdge!]!
    pageInfo: PageInfo!
}

type UserEdge {
    node: User!
    cursor: String!
}

type PageInfo {
    hasNextPage: Boolean!
    endCursor: String
}
```

### Filtering

```graphql
type Query {
    users(filter: UserFilter): [User!]!
}

input UserFilter {
    username: String
    email: String
    role: UserRole
}
```

## Mutation Design

### Standard Pattern

```graphql
type Mutation {
    createUser(input: CreateUserInput!): CreateUserPayload!
    updateUser(input: UpdateUserInput!): UpdateUserPayload!
    deleteUser(input: DeleteUserInput!): DeleteUserPayload!
}

type CreateUserPayload {
    user: User!
    errors: [Error!]
}

type Error {
    field: String!
    message: String!
}
```

## Subscription Design

```graphql
type Subscription {
    userCreated: User!
    postUpdated(userId: ID!): Post!
}
```

## Best Practices

1. **Nullability**
   - Make fields non-null unless optional
   - Consider lists of non-null items

2. **Naming Conventions**
   - Use camelCase for fields
   - Use PascalCase for types
   - Be consistent with plurals

3. **Type Extensions**
   ```graphql
   extend type Query {
       newField: String!
   }
   ```

4. **Interfaces**
   ```graphql
   interface Node {
       id: ID!
   }

   interface Timestampable {
       createdAt: DateTime!
       updatedAt: DateTime!
   }
   ```

5. **Custom Scalars**
   ```graphql
   scalar DateTime
   scalar JSON
   scalar Upload
   ```

## Schema Organization

1. Break into domains
2. Use type extensions
3. Implement interfaces
4. Consider schema stitching
5. Plan for versioning

## Performance Considerations

1. Depth limiting
2. Complexity analysis
3. Field selection optimization
4. Batching strategies
5. Caching approaches