# IBM MQ Producer

Spring Boot application for producing messages to IBM MQ.

## Prerequisites

- Docker daemon running
- IBM MQ container running (via docker-compose)

## Running the Application

### 1. Start IBM MQ Container

```bash
cd ibm-mq/docker
docker-compose --profile dev up -d
```

This will start IBM MQ on:
- Port 1414 (MQ)
- Port 9443 (Web Console)
- Port 9157 (Metrics)

### 2. Run the Producer Application

```bash
./gradlew :ibm-mq:producer:bootRun
```

The application will start on port 8080.

## API Endpoints

### Send a Single Message

```bash
curl -X POST http://localhost:8080/api/messages \
  -H "Content-Type: application/json" \
  -d '{
    "content": "Hello IBM MQ!",
    "priority": 5,
    "metadata": {
      "source": "test-client",
      "type": "greeting"
    }
  }'
```

### Send Batch Messages

```bash
curl -X POST http://localhost:8080/api/messages/batch \
  -H "Content-Type: application/json" \
  -d '[
    {
      "content": "Message 1",
      "priority": 4
    },
    {
      "content": "Message 2",
      "priority": 6,
      "metadata": {
        "urgent": "true"
      }
    }
  ]'
```

### Health Check

```bash
curl http://localhost:8080/api/messages/health
```

## Configuration

Configuration is located in `src/main/resources/application.yml`:

- `ibm.mq.queueManager`: Queue manager name (default: QM1)
- `ibm.mq.channel`: Channel name (default: DEV.ADMIN.SVRCONN)
- `ibm.mq.connName`: Connection string (default: localhost(1414))
- `ibm.mq.user`: MQ user (default: admin)
- `ibm.mq.password`: MQ password (default: passw0rd)
- `mq.queue-name`: Queue name (default: DEV.QUEUE.1)

## Testing

```bash
./gradlew :ibm-mq:producer:test
```

Tests will automatically start a test IBM MQ container on port 1415.

## MQ Web Console

Access the IBM MQ Web Console at: https://localhost:9443/ibmmq/console/

Default credentials:
- Username: admin
- Password: passw0rd

## Message Priority

Messages can be sent with priority levels from 0 (lowest) to 9 (highest). Default is 4.

## Metadata

You can attach custom metadata to messages using the `metadata` field. These will be added as JMS properties.
