# ---- Build Stage ----
FROM maven:3.9.9-eclipse-temurin-21 AS builder

WORKDIR /app

# Copy only pom.xml first to leverage Docker layer caching for dependencies
COPY pom.xml .

# Download dependencies
RUN mvn dependency:go-offline -B

# Copy the source code
COPY src ./src

# Build the application
RUN mvn clean package -DskipTests

# ---- Run Stage ----
FROM openjdk:21-jdk AS runner

WORKDIR /app

# Copy the jar from the build stage
COPY --from=builder /app/target/aufgabenliste-1.0-SNAPSHOT.jar ./app.jar

# Expose the port your Spring Boot app runs on
EXPOSE 4000

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]