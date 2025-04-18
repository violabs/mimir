# Mimir Documentation

Welcome to the Mimir project documentation. This documentation is organized to help you find information efficiently, whether you're getting started or looking for detailed technical information.

## Documentation Structure

### Module READMEs
Each module contains its own README.md with:
- Quick setup instructions
- Basic configuration
- Common usage patterns
- Module-specific troubleshooting
- Direct dependencies
- Getting started guide

### Central Documentation
The `/docs` folder contains comprehensive documentation:

#### [Architecture](./architecture)
- [Decisions](./architecture/decisions) - Architecture Decision Records (ADRs)
- [Diagrams](./architecture/diagrams) - System architecture diagrams
- [Overview](./architecture/overview.md) - High-level architecture description

#### [Core](./core)
- [Configuration](./core/configuration.md) - Core module configuration
- [Extensions](./core/extensions.md) - Extending core functionality

#### [Databases](./databases)
- [Comparison](./databases/comparison.md) - MySQL vs PostgreSQL implementation
- [Migration](./databases/migration.md) - Database migration guides
- [Optimization](./databases/optimization.md) - Cross-database optimization

#### [Deployment](./deployment)
- [Docker](./deployment/docker.md) - Docker deployment guide
- [Kubernetes](./deployment/kubernetes.md) - Kubernetes deployment
- [Monitoring](./deployment/monitoring.md) - Monitoring setup

#### [Development](./development)
- [Environment](./development/environment.md) - Dev environment setup
- [Guidelines](./development/guidelines.md) - Coding standards
- [Tools](./development/tools.md) - Development tools

#### [Messaging](./messaging)
- [Kafka Patterns](./messaging/kafka-patterns.md) - Kafka usage patterns
- [Security](./messaging/security.md) - Messaging security

#### [Testing](./testing)
- [Integration](./testing/integration.md) - Integration testing
- [Performance](./testing/performance.md) - Performance testing
- [Strategies](./testing/strategies.md) - Testing practices

#### [Vector Databases](./vector-databases)
- [Comparison](./vector-databases/comparison.md) - Vector DB comparison
- [Optimization](./vector-databases/optimization.md) - Search optimization

## Quick Links
- [Quick Start Guide](./quick-start.md)
- [Contributing Guidelines](./contributing.md)
- [Architecture Overview](./architecture/overview.md)

## Documentation Guidelines

### For Developers
1. Module-specific documentation goes in the module's README.md
2. Cross-module concerns go in the appropriate `/docs` section
3. Keep code examples up to date with the actual codebase
4. Include practical examples for complex features
5. Document both successful and error scenarios

### For Contributors
1. Follow the structure outlined in this document
2. Update documentation alongside code changes
3. Include diagrams for complex flows (in architecture/diagrams)
4. Reference related documentation when appropriate
5. Keep the language clear and consistent

## Getting Help
If you can't find what you're looking for:
1. Check the module-specific README first
2. Look for relevant sections in `/docs`
3. Submit an issue if documentation is missing or unclear
4. Contribute improvements via pull requests

## License
This documentation is part of the Mimir project and is licensed under the same terms as the main project.