
# Overview

# Local Setup

This project utilizes containers to run local, test, and integrations.

## Dev

### Start
```shell
docker compose --profile=dev -f ./docker/docker-compose.yml up
```

### Stop
```shell
docker compose --profile=dev -f ./docker/docker-compose.yml stop
```

### Remove
```shell
docker compose --profile=dev -f ./docker/docker-compose.yml down
```

## Test

### Start
```shell
docker compose --profile=test -f ./docker/docker-compose.yml up
```

### Stop
```shell
docker compose --profile=test -f ./docker/docker-compose.yml stop
```

### Remove
```shell
docker compose --profile=test -f ./docker/docker-compose.yml down
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