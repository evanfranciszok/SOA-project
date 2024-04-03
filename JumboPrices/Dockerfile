# Build stage: Use the official Gradle image to build the project with JDK 17
FROM gradle:8.7.0-jdk17-alpine AS build

# Set the working directory in the container to /home/gradle/project
WORKDIR /home/gradle/project

# Copy the Gradle build file(s) and source code into the container
COPY --chown=gradle:gradle gradlew .
COPY --chown=gradle:gradle gradle gradle
COPY --chown=gradle:gradle build.gradle .
COPY --chown=gradle:gradle settings.gradle .
COPY --chown=gradle:gradle src src

# Build the application (with tests skipped for build speed)
RUN gradle clean build -x test

# Runtime stage: Use the Eclipse Temurin JDK 17 image for a smaller runtime image
FROM eclipse-temurin:17-jre as runtime

# Create a non-root user and switch to it
RUN groupadd -r appuser && useradd -r -g appuser -d /app -s /sbin/nologin -c "Docker application user" appuser
USER appuser

# Set the deployment directory
WORKDIR /app

# Copy the built jar file from the build stage to the deployment directory
COPY --from=build /home/gradle/project/build/libs/*.jar ./app.jar

# Expose the application's port
EXPOSE 8080

# Run the Spring Boot application
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app/app.jar"]
