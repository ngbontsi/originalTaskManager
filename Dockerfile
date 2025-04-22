# Use Eclipse Temurin JDK 23 as base image
FROM eclipse-temurin:23-jdk

# Set working directory inside container
WORKDIR /app

# Copy the built jar file into the container
COPY target/originalTaskManager-1.0-SNAPSHOT.jar app.jar

# Run the jar file
ENTRYPOINT ["java", "-jar", "app.jar"]
