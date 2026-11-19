FROM eclipse-temurin:21-jdk-jammy AS build

WORKDIR /workspace

COPY . .
RUN chmod +x mvnw && ./mvnw --batch-mode --no-transfer-progress package

FROM eclipse-temurin:21-jre-jammy AS runtime

WORKDIR /app

COPY --from=build /workspace/target/hello-world-api-*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
