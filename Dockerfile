## --- Build stage ---
FROM maven:3.9.9-eclipse-temurin-17 AS build

WORKDIR /app

# for better layer caching
COPY pom.xml .
COPY .mvn .mvn
COPY mvnw .

RUN ./mvnw -q -DskipTests dependency:go-offline

## Copy source and build
COPY src src
RUN ./mvnw -q -DskipTests package

## --- Run stage ---
FROM eclipse-temurin:17-jre
WORKDIR /app

# Copy built jar from build stage
COPY --from=build /app/target/app.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","/app/app.jar"]