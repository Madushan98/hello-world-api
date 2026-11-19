# Hello World API

A simple Java 21 / Spring Boot application built with Maven. No database is required.

## API

`GET /hello-world?name=alice`

| Input | Status | Response |
| --- | --- | --- |
| `alice` | 200 | `{"message":"Hello Alice"}` |
| `nancy` | 400 | `{"error":"Invalid Input"}` |
| Missing or empty `name` | 400 | `{"error":"Invalid Input"}` |

## Assumptions

- Names must start with an English letter A–M, ignoring case. Other initial characters are invalid.
- Surrounding whitespace is removed before validation.
- Only the first letter is capitalized; the remaining characters keep their original spelling and case.
- Exactly one `name` parameter is required. Missing, empty, whitespace-only, or repeated values return HTTP 400 with `{"error":"Invalid Input"}`.
- Validation checks the first character only; no additional restrictions are applied to the remaining characters.

## Run locally

Requires JDK 21 or later with `JAVA_HOME` set. The Maven wrapper is included; internet access is needed for the first build. Run commands from the project root.

```sh
sh ./mvnw spring-boot:run
```

On Windows PowerShell, use `.\mvnw.cmd` instead of `sh ./mvnw` for all Maven commands.

The API runs at `http://localhost:8080`. Stop it with Ctrl+C.

```sh
curl -i "http://localhost:8080/hello-world?name=alice"
```

On older Windows PowerShell versions, use `curl.exe`.

## Swagger / OpenAPI

Start the application, then open [Swagger UI](http://localhost:8080/swagger-ui/index.html).
Expand `GET /hello-world`, click **Try it out**, enter a name such as `alice`, and click **Execute**.
The documentation includes the required `name` query parameter, validation rules, and example 200/400 responses.

The OpenAPI specification is available as [JSON](http://localhost:8080/v3/api-docs)
or [YAML](http://localhost:8080/v3/api-docs.yaml). These URLs also work when running with Docker.

## Run with Docker

Requires Docker running with Linux containers enabled and internet access. Java and Maven are supplied by the build.

```sh
docker build -t hello-world-api .
docker run --rm -p 8080:8080 hello-world-api
```

The build runs the tests. Open `http://localhost:8080/hello-world?name=alice`; stop with Ctrl+C.

## Test and build

Run the 20 service, controller, and HTTP test cases:

```sh
sh ./mvnw clean test
```

Build and run an executable JAR:

```sh
sh ./mvnw clean verify
java -jar target/hello-world-api-1.0.0.jar
```

## Structure

```text
src/main/java/com/madushan/helloworld/
  HelloWorldApplication.java
  controller/    HTTP endpoints
  service/       Validation and greeting logic
  dto/           Response records
  exceptions/    Validation exception and error handler
src/test/java/com/madushan/helloworld/
```
