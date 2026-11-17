
# ==========================================
# STAGE 1: BUILD
# ==========================================

FROM eclipse-temurin:21-jdk-jammy AS build

WORKDIR /workspace

# Copy Maven Wrapper configuration
COPY .mvn/ .mvn/
COPY mvnw pom.xml ./

# Make Maven Wrapper executable
RUN chmod +x ./mvnw

# Download dependencies separately for better build caching
RUN ./mvnw --batch-mode dependency:go-offline

# Copy application source code
COPY src/ src/

# Run tests and package executable Spring Boot JAR
RUN ./mvnw --batch-mode clean verify


# ==========================================
# STAGE 2: RUNTIME
# ==========================================

FROM eclipse-temurin:21-jre-jammy AS runtime

WORKDIR /app

# Create a dedicated non-root user and group
RUN groupadd --gid 10001 taskdock \
    && useradd --uid 10001 \
                 --gid taskdock \
                 --home-dir /app \
                 --no-create-home \
                 taskdock

# Copy only the executable JAR from build stage
COPY --from=build --chown=taskdock:taskdock \
    /workspace/target/*.jar \
    /app/app.jar

# Document application port
EXPOSE 8080

# Run as non-root user
USER taskdock:taskdock

# Start Spring Boot application
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
