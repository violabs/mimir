
# Overview

# Local Setup

This project utilizes containers to run local, test, and integrations.

Either install Docker Desktop if non-commercial or use Colima or Podman.
Run the use these commands to spin up the different docker containers based
on profile.

## Ollama
You either will want to download ollama, or use the container associated
in the docker file. you can use:

### Start
```shell
docker compose --profile=ollama up
```

### Stop
```shell
docker compose --profile=ollama stop
```

### Remove
```shell
docker compose --profile=ollama down
```

## Dev

Once you have Ollama you can start the dev setup

### Container Management
#### Start
```shell
docker compose --profile=dev -f ./docker/docker-compose.yml up
```

#### Stop
```shell
docker compose --profile=dev -f ./docker/docker-compose.yml stop
```

#### Remove
```shell
docker compose --profile=dev -f ./docker/docker-compose.yml down
```

### Running the app

With IntelliJ `[RUN] weaviate`

With gradle
```shell
../../gradlew bootRun -Dspring.profiles.active=dev
```

### Endpoint and Examples

Once it is running you can navigate to swagger at:
http://localhost:8081/swagger-ui/index.html

**Example Add Sentences Request**
```json
{
  "sentences": [
    "Once upon a time",
    "A long time ago",
    "Time enough at last"
  ],
  "country": "US",
  "year": 2025
}
```

Then you can query using the search endpoint for things like `time` and `seconds`

## Test
### Container Management
#### Start
```shell
docker compose --profile=test -f ./docker/docker-compose.yml up
```

#### Stop
```shell
docker compose --profile=test -f ./docker/docker-compose.yml stop
```

#### Remove
```shell
docker compose --profile=test -f ./docker/docker-compose.yml down
```

### Running Test

Run with IntelliJ `[TEST] weaviate`

With gradle
```shell
./gradlew vector:weaviate:test
```

## E2E
### Start
```shell
docker compose --profile=e2e -f ./docker/docker-compose.yml up
```

### Stop
```shell
docker compose --profile=e2e -f ./docker/docker-compose.yml stop
```

### Remove
```shell
docker compose --profile=e2e -f ./docker/docker-compose.yml down
```