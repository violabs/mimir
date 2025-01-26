#!/bin/sh

echo "Current directory contents:"
ls -la /app/

echo "Jar file details:"
ls -la /app/app.jar

# Default profile if none specified
PROFILE=${SPRING_PROFILES_ACTIVE:-default}

echo "Starting with profile: ${PROFILE}"
exec java "${JAVA_OPTS}" -jar -Dspring.profiles.active="${PROFILE}" /app/app.jar