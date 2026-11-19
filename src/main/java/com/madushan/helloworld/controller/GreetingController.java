package com.madushan.helloworld.controller;

import java.util.List;

import com.madushan.helloworld.dto.GreetingResponse;
import com.madushan.helloworld.dto.ErrorResponse;
import com.madushan.helloworld.exceptions.InvalidNameException;
import com.madushan.helloworld.service.GreetingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

    private final GreetingService greetingService;

    public GreetingController(GreetingService greetingService) {
        this.greetingService = greetingService;
    }

    @GetMapping(value = "/hello-world", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Greet a person",
            description = "Provide exactly one name starting with A-M, ignoring case. "
                    + "Surrounding whitespace is removed and only the first letter is capitalized.",
            parameters = @Parameter(name = "name", in = ParameterIn.QUERY, required = true,
                    description = "The name to greet. Missing, blank, repeated, or invalid names return HTTP 400.",
                    example = "alice", schema = @Schema(type = "string")))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Greeting generated",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = GreetingResponse.class),
                            examples = @ExampleObject(value = "{\"message\":\"Hello Alice\"}"))),
            @ApiResponse(responseCode = "400", description = "Invalid name input",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(value = "{\"error\":\"Invalid Input\"}")))
    })
    public GreetingResponse helloWorld(
            @Parameter(hidden = true) @RequestParam MultiValueMap<String, String> parameters) {
        List<String> names = parameters.get("name");
        if (names == null || names.size() != 1) {
            throw new InvalidNameException();
        }

        return new GreetingResponse(greetingService.greet(names.get(0)));
    }
}
