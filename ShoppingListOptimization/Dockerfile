# Start with a base image containing Java runtime (specify the tag based on your Java version requirement)
FROM openjdk:11-jre-slim

# Add Maintainer Info
LABEL maintainer="your_email@example.com"

# Add a volume pointing to /tmp
VOLUME /tmp

# Make port 8080 available to the world outside this container
EXPOSE 8080

# The application's jar file (adjust the jar file name based on your project's artifactId and version as defined in your pom.xml)
ARG JAR_FILE=target/*.jar

# Add the application's jar to the container
ADD ${JAR_FILE} app.jar

# Run the jar file 
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
