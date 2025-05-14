# ---- Stage 1: Build the jar ----
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# ---- Stage 2: Run the jar ----
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/SocialMedia-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
