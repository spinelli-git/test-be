# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Build & Run Commands

```bash
# Build the project
./gradlew build

# Run the application
./gradlew bootRun

# Run all tests
./gradlew test

# Run a single test class
./gradlew test --tests "com.project.test.TestApplicationTests"

# Clean build artifacts
./gradlew clean
```

On Windows, use `gradlew.bat` instead of `./gradlew`.

## Architecture

This is a **Spring Boot 3.2.1 + WebFlux** (reactive) backend. Java 17, Gradle 8.5.

**Package structure:** `com.project.test`
- `TestApplication.java` — entry point
- `config/WebConfig.java` — CORS configuration (allows `http://localhost:4200`, i.e. an Angular frontend)
- `controller/FileController.java` — REST controller

**Key design pattern:** The application uses Project Reactor (`Flux`) for reactive streaming. `FileController` exposes a Server-Sent Events (SSE) endpoint at `GET /stream-file` (`text/event-stream`) that reads a local file line-by-line and streams each line as an SSE event with a 50ms delay between elements.

**Note:** The file path in `FileController` is hardcoded to `C:/Users/simon/Desktop/updateCV.txt`. This will need to be updated or made configurable for different environments.
