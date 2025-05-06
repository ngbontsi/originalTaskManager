# Use lightweight JDK base image
FROM eclipse-temurin:21-jdk-alpine

# Set environment variables
ENV APP_HOME=/app
WORKDIR $APP_HOME

# Copy the JAR file from the local Maven build
COPY target/originaltaskmanager-1.0-SNAPSHOT.jar app.jar

# Expose the Spring Boot default port
EXPOSE 8080

# Run the JAR
ENTRYPOINT ["java", "-jar", "app.jar"]
