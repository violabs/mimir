# Contributing to Mimir

## Adding New Examples

1. Create a new module:
```bash
mkdir newModule
touch newModule/build.gradle.kts
```

2. Update settings.gradle.kts:
```kotlin
include("newModule")
```

3. Implement required files:
- README.md
- docker-compose.yml
- Documentation in /docs

## Code Style

### Kotlin
- Follow [Kotlin Coding Conventions](https://kotlinlang.org/docs/coding-conventions.html)
- Use [ktlint](https://github.com/pinterest/ktlint)

### Tests
- Write in Groovy using Spock Framework
- Follow BDD style: given/when/then
- Include integration tests

## PR Process

1. Fork repository
2. Create feature branch
3. Implement changes
4. Add tests
5. Update documentation
6. Submit PR

## Documentation

- Update README.md
- Add detailed docs in /docs
- Include examples
- Update module-specific guides