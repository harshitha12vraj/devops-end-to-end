# =========================
# Stage 1: Build application
# =========================
FROM maven:3.9-eclipse-temurin-21 AS builder

WORKDIR /build

# Copy pom first for better Docker layer caching
COPY pom.xml .

RUN mvn dependency:go-offline -B

# Copy source code
COPY src ./src

# Compile, test and package
RUN mvn clean package -B


# =========================
# Stage 2: Runtime
# =========================
FROM eclipse-temurin:21-jre-jammy

WORKDIR /app

# Create non-root user
RUN groupadd --system spring && \
    useradd --system --gid spring spring

# Copy only the JAR from build stage
COPY --from=builder /build/target/backend-0.0.1-SNAPSHOT.jar app.jar

RUN chown spring:spring app.jar

USER spring

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "app.jar"]