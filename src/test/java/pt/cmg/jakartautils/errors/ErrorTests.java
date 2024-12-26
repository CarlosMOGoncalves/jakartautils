/*
 * Copyright (c) 2024 Carlos Gonçalves (https://www.linkedin.com/in/carlosmogoncalves/)
 * Likely open-source, so copy at will, bugs will be yours as well.
 */
package pt.cmg.jakartautils.errors;

import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Named;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * @author Carlos Gonçalves
 */
@Execution(value = ExecutionMode.CONCURRENT)
class ErrorTests {

    @ParameterizedTest
    @MethodSource("provideEmptyStrings")
    @DisplayName("Empty messages should return an unspecified Error message")
    void givenEmptyMessage_whenCreatingError_thenReturnUnspecified(String errorMessage) {
        ErrorDTO nullMessageError = ErrorDTO.fromErrorMessage(errorMessage);
        assertEquals(ErrorDTO.UNSPECIFIED_ERROR, nullMessageError);
    }

    private static Stream<Arguments> provideEmptyStrings() {
        return Stream.of(
            Arguments.of(Named.of("String vazia simples", "")),
            Arguments.of(Named.of("String vazia com espaços", " ")),
            Arguments.of(Named.of("String vazia tab", "\t")),
            Arguments.of(Named.of("String vazia \n", "\n")));
    }

    @Test
    @DisplayName("Null messages should return an unspecified Error message")
    void givenNullMessage_whenCreatingError_thenReturnUnspecified() {
        ErrorDTO nullMessageError = ErrorDTO.fromErrorMessage(null);
        assertEquals(ErrorDTO.UNSPECIFIED_ERROR, nullMessageError);
    }

    @ParameterizedTest
    @ValueSource(strings = {"Incomplete form", "IncompleteForm"})
    @DisplayName("Messages without error code should return an Error with code 0")
    void givenNoErrorCode_whenCreatingError_thenReturnZeroErrorCode(String errorMessage) {
        ErrorDTO emptyMessageError = ErrorDTO.fromErrorMessage(errorMessage);
        assertEquals(0, emptyMessageError.code);
    }

    @ParameterizedTest
    @ValueSource(strings = {"1-Incomplete form", "1 - Incomplete form", "1- "})
    @DisplayName("Messages with error codes should return a valid ErrorDTO")
    void givenValidMessage_whenCreatingError_thenReturnValidErrorDTO(String errorMessage) {
        ErrorDTO actualErrorDTO = ErrorDTO.fromErrorMessage(errorMessage);

        String[] errors = errorMessage.split("-");
        int expectedErrorCode = Integer.parseInt(StringUtils.trim(errors[0]));
        String expectedMessage = StringUtils.trim(errors[1]);

        assertEquals(expectedErrorCode, actualErrorDTO.code);
        assertEquals(expectedMessage, actualErrorDTO.description);
    }

    @ParameterizedTest
    @ValueSource(strings = {"A-Incomplete form", "1.1 - Incomplete form"})
    @DisplayName("Invalid error codes return a zero-code ErrorDTO")
    void givenInvalidErrorNumber_whenCreatingError_thenReturnZeroErrorCode(String errorMessage) {
        ErrorDTO actualErrorDTO = ErrorDTO.fromErrorMessage(errorMessage);

        assertEquals(0, actualErrorDTO.code);
    }

}
