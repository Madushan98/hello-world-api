## Summary

Implemented `GET /hello-world?name=alice` using Java 21, Spring Boot, and Maven. Names beginning with A-M return HTTP 200 with `{"message":"Hello Alice"}`; names beginning with N-Z and missing or empty input return HTTP 400 with `{"error":"Invalid Input"}`.

## Solution rationale

- A small controller handles HTTP input, and a service owns validation and formatting.
- Explicit ASCII ranges implement the English alphabet rule without locale-dependent comparisons.
- Constructor injection exposes dependencies; records keep response objects small and immutable.
- A dedicated validation exception and exception handler produce the required error response.
- Jackson serializes responses safely. Surrounding whitespace is stripped, the first letter is capitalized, and the rest of the spelling is retained.
- Repeated `name` parameters are rejected. Assumptions and framework-level behavior are documented in the README.
- The Maven wrapper and Java 21 CI workflow provide a repeatable build.

## Validation

Run `sh ./mvnw clean verify` (Windows: `.\mvnw.cmd clean verify`). The 20 test cases check representative names, the M/N boundary in both cases, missing and blank input, numeric and non-English initials, exact HTTP/JSON responses, duplicate parameters, unsupported methods, URL decoding, and JSON escaping. Three tests make real requests through embedded Tomcat.

## walkthrough of the code

Video walkthrough: [https://drive.google.com/file/d/15TQEV26emzKhO-tzMmlEc3q9XsnEuKNM/view?usp=sharing](https://drive.google.com/file/d/15TQEV26emzKhO-tzMmlEc3q9XsnEuKNM/view?usp=sharing)
