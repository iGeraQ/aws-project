FROM eclipse-temurin:21-jdk-alpine AS builder
WORKDIR /app

COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .

RUN chmod +x gradlew
RUN ./gradlew dependencies --no-daemon || true

COPY src src

# Production build stage
FROM builder AS build-stage
RUN ./gradlew bootJar --no-daemon

# Production runtime stage
FROM eclipse-temurin:21-jre-alpine AS production
WORKDIR /app
COPY --from=build-stage /app/build/libs/*-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]

# Development stage (for hot reload)
FROM builder AS development
WORKDIR /app
EXPOSE 8080
# Use bootRun with continuous mode for development
ENTRYPOINT ["./gradlew", "bootRun", "--no-daemon", "-t"]
