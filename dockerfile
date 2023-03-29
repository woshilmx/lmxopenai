FROM maven:3.8.1-jdk-8-slim as builder

MAINTAINER lmx

# Copy local code to the container image.
WORKDIR /app
COPY pom.xml .
COPY src ./src

# Build a release artifact.
RUN mvn package -DskipTests

# 声明环境变量，这样容器就可以在运行时访问它们
ENV OPENAI_API_KEY=你的API_KEY

# Run the web service on container startup.
ENTRYPOINT ["java","-jar","/app/target/openAIService-1.0-SNAPSHOT.jar"]