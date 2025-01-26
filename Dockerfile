# Base Dockerfile
ARG SERVICE_NAME
ARG SERVICE_JAR_NAME
ARG VERSION

# ----------------------------
# 1) Builder stage
# ----------------------------
FROM eclipse-temurin:21-jdk-ubi9-minimal@sha256:8e8fb36b437a4aa3a0730ccc3dc43aa38b7ea6f9a684ff35f746d67c1fa7e25d AS builder

# Get the build arguments in this stage
ARG SERVICE_NAME
ARG SERVICE_JAR_NAME
ARG VERSION

# Install required tools for Gradle
RUN microdnf install -y findutils tar && microdnf clean all

# Copy Gradle files
COPY gradlew settings.gradle.kts build.gradle.kts gradle.properties /app/
COPY gradle /app/gradle

# Copy all shared/required modules
COPY artax /app/artax
COPY buildSrc /app/buildSrc

# Copy the target service
COPY ${SERVICE_NAME} /app/${SERVICE_NAME}

# Change to the app dir
WORKDIR /app

# Build the application
RUN ./gradlew --no-daemon ${SERVICE_NAME}:build -x ${SERVICE_NAME}:test

# Check output
RUN echo "Checking build output:" && \
    ls -la /app/${SERVICE_NAME}/build/libs/ && \
    echo "SERVICE_NAME: ${SERVICE_NAME}" && \
    echo "SERVICE_JAR_NAME: ${SERVICE_JAR_NAME}" && \
    echo "VERSION: ${VERSION}"

# ----------------------------
# 2) Final stage
# ----------------------------
FROM eclipse-temurin:21-jre-ubi9-minimal@sha256:a42ea314a5c147aa575718dd8d161172eb1f9dde236d2c3842b5450eb9d9fb8c

# Get the build arguments in this stage
ARG SERVICE_NAME
ARG SERVICE_JAR_NAME
ARG VERSION

# Create and switch to a non-root user
RUN groupadd --system appgroup && useradd --system --gid appgroup appuser
USER appuser

WORKDIR /app

# Copy the JAR file from the builder stage
COPY --from=builder /app/${SERVICE_NAME}/build/libs/${SERVICE_JAR_NAME}-${VERSION}.jar app.jar

EXPOSE 8080

COPY --chmod=0755 entrypoint.sh /
ENTRYPOINT ["/entrypoint.sh"]