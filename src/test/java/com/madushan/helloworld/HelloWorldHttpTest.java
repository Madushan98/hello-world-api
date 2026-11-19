package com.madushan.helloworld;

import java.net.URI;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class HelloWorldHttpTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate client;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void decodesEncodedWhitespace() throws Exception {
        assertResponse("?name=%20alice%20", HttpStatus.OK, "message", "Hello Alice");
    }

    @Test
    void escapesJsonSpecialCharactersWithoutChangingTheName() throws Exception {
        assertResponse("?name=alice%22%5C", HttpStatus.OK, "message", "Hello Alice\"\\");
    }

    @Test
    void rejectsAnEmptyNameOverHttp() throws Exception {
        assertResponse("?name=", HttpStatus.BAD_REQUEST, "error", "Invalid Input");
    }

    private void assertResponse(String query, HttpStatus status, String key, String value) throws Exception {
        URI uri = URI.create("http://localhost:" + port + "/hello-world" + query);
        ResponseEntity<String> response = client.getForEntity(uri, String.class);

        assertThat(response.getStatusCode()).isEqualTo(status);
        assertThat(response.getHeaders().getContentType()).isNotNull();
        assertThat(response.getHeaders().getContentType().isCompatibleWith(MediaType.APPLICATION_JSON)).isTrue();
        JsonNode json = objectMapper.readTree(response.getBody());
        assertThat(json.size()).isEqualTo(1);
        assertThat(json.get(key).asText()).isEqualTo(value);
    }
}
