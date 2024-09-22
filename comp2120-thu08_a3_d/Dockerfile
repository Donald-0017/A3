# First stage: Build the project using Gradle
FROM gradle:8.10.1-jdk21 AS build

# Set the working directory in the container
WORKDIR /app

# Copy only Gradle wrapper and build scripts to leverage Docker cache
COPY gradle ./gradle
COPY gradlew .
COPY app/build.gradle .
COPY settings.gradle .

# Download dependencies without building (optional for caching)
RUN ./gradlew dependencies --no-daemon

# Copy the rest of the application files
COPY app/src ./src

# Build the application
RUN ./gradlew build --no-daemon

# Second stage: Create a smaller image using only the JRE
FROM eclipse-temurin:21-jre

# Set working directory in the runtime image
WORKDIR /app

# Copy the built JAR file from the build stage
COPY --from=build /app/build/libs/*.jar /app/app.jar


# Command to run the application
CMD ["java", "-jar", "/app/app.jar"]
