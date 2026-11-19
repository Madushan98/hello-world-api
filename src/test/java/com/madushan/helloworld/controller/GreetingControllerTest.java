package com.madushan.helloworld.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.json.JsonCompareMode;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class GreetingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void returnsExpectedSuccessBody() throws Exception {
        mockMvc.perform(get("/hello-world").param("name", "alice"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"message\":\"Hello Alice\"}", JsonCompareMode.STRICT));
    }

    @Test
    void returnsExactErrorBodyForAnInvalidName() throws Exception {
        mockMvc.perform(get("/hello-world").param("name", "nancy"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"error\":\"Invalid Input\"}", JsonCompareMode.STRICT));
    }

    @Test
    void returnsBadRequestWhenNameIsMissing() throws Exception {
        mockMvc.perform(get("/hello-world"))
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{\"error\":\"Invalid Input\"}", JsonCompareMode.STRICT));
    }

    @Test
    void rejectsRepeatedNameParameters() throws Exception {
        mockMvc.perform(get("/hello-world").param("name", "alice", "bob"))
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{\"error\":\"Invalid Input\"}", JsonCompareMode.STRICT));
    }

    @Test
    void rejectsUnsupportedHttpMethods() throws Exception {
        mockMvc.perform(post("/hello-world").param("name", "alice"))
                .andExpect(status().isMethodNotAllowed());
    }
}
