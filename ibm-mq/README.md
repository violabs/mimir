# IBM MQ

> REQUIRED: Docker daemon or equivalent runner

## Running IBM MQ

The IBM MQ container can be started using Docker Compose:

```bash
cd docker
docker-compose --profile dev up -d
```

## Sub-projects

- **producer**: Spring Boot application for producing messages to IBM MQ
- **consumer**: Spring Boot application for consuming messages from IBM MQ (coming soon)

## Getting Started

See the README in each sub-project for detailed instructions.