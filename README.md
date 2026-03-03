# test-be

Backend Spring Boot reattivo che espone un file di testo in streaming tramite Server-Sent Events (SSE).

## Stack

- Java 17
- Spring Boot 3.2.1
- Spring WebFlux (reactive)
- Gradle 8.5

## API

### `GET /stream-file`

Legge un file di testo riga per riga e lo invia al client come stream SSE (`text/event-stream`), con un ritardo di 50ms tra una riga e l'altra.

**CORS:** configurato per `http://localhost:4200` (frontend Angular).

## Avvio

```bash
./gradlew bootRun
```

## Build

```bash
./gradlew build
```

## Test

```bash
./gradlew test
```
