/*
 * Copyright (c) 2024 Carlos Gonçalves (https://www.linkedin.com/in/carlosmogoncalves/)
 * Likely open-source, so copy at will, bugs will be yours as well.
 */
package pt.cmg.jakartautils.text;

import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Named;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * @author Carlos Gonçalves
 */
public class TextFormatterTests {

    private static final String CLEAN_STRING = "This is a clean string";

    @Test
    @DisplayName("Sanitised strings should not be changed")
    void givenSanitisedString_whenCleaningText_thenReturnStringUnchanged() {
        assertEquals(CLEAN_STRING, TextFormatter.cleanText(CLEAN_STRING));
    }

    @ParameterizedTest
    @MethodSource("provideUnsanitisedStrings")
    @DisplayName("Unsanitised strings should be cleaned of speacial characters")
    void givenStringMessage_whenCleaningText_thenReturnStringWithoutSpecialCharacters(String message) {
        assertEquals(CLEAN_STRING, TextFormatter.cleanText(message));
    }

    private static Stream<Arguments> provideUnsanitisedStrings() {
        return Stream.of(
            Arguments.of(Named.of("String without special characters", "This is a clean string")),
            Arguments.of(Named.of("String with leading spaces", " This is a clean string")),
            Arguments.of(Named.of("String with trailing spaces", "This is a clean string ")),
            Arguments.of(Named.of("String with trailing and leading spaces", " This is a clean string ")),
            Arguments.of(Named.of("String with \\t", "This\tis\ta\tclean\tstring")),
            Arguments.of(Named.of("String with \\n", "This\nis\na\nclean\nstring")),
            Arguments.of(Named.of("String with \\r", "This\ris\ra\rclean\rstring")),
            Arguments.of(Named.of("String with \\b", "This\bis\ba\bclean\bstring\b")));
    }

    @ParameterizedTest
    @MethodSource("provideNullStrings")
    @DisplayName("Null or empty Strings are cleaned to empty string")
    void givenNullOrEmptyStrings_whenCleaningText_thenReturnEmptyString(String message) {
        assertEquals("", TextFormatter.cleanText(message));
    }

    private static Stream<Arguments> provideNullStrings() {
        return Stream.of(
            Arguments.of(Named.of("String without characters", "")),
            Arguments.of(Named.of("Null String", null)));
    }

    @ParameterizedTest
    @CsvFileSource(files = "src/test/resources/text_to_remove_chars.csv", useHeadersInDisplayName = true)
    @DisplayName("Random characters passed as parameters will be removed")
    void givenCharactersAndStrings_whenCleaningText_thenRemoveThoseCharacters(String message, char charToRemove) {
        assertEquals(CLEAN_STRING, TextFormatter.cleanText(message, charToRemove));
    }

}
