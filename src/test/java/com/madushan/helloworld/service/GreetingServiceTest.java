package com.madushan.helloworld.service;

import com.madushan.helloworld.exceptions.InvalidNameException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class GreetingServiceTest {

    private final GreetingService service = new GreetingService();

    @ParameterizedTest
    @CsvSource({
            "alice, Hello Alice",
            "Alice, Hello Alice",
            "m, Hello M",
            "M, Hello M",
            "aLICE, Hello ALICE"
    })
    void greetsValidNamesAndCapitalizesOnlyTheFirstLetter(String input, String expected) {
        assertThat(service.greet(input)).isEqualTo(expected);
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "n", "N", "1alice", "éva"})
    void rejectsInvalidNames(String input) {
        assertInvalid(input);
    }

    private void assertInvalid(String input) {
        assertThatThrownBy(() -> service.greet(input))
                .isInstanceOf(InvalidNameException.class)
                .hasMessage("Invalid Input");
    }
}
