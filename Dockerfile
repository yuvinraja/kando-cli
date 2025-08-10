# Use OpenJDK 17 slim image
FROM openjdk:17-jre-slim

# Set working directory
WORKDIR /app

# Copy the JAR file
COPY target/kando.jar /app/kando.jar

# Create a non-root user
RUN groupadd -r kando && useradd -r -g kando kando
RUN chown -R kando:kando /app

# Switch to non-root user
USER kando

# Set entry point
ENTRYPOINT ["java", "-jar", "/app/kando.jar"]

# Default command (show help)
CMD ["--help"]

# Labels
LABEL maintainer="Yuvin Raja <your-email@example.com>"
LABEL description="Kando CLI - Local Kanban for Projects"
LABEL version="1.0.0"
