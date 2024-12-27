/**
 * Copyright (c) 2024 Carlos Gonçalves (https://www.linkedin.com/in/carlosmogoncalves/)
 * Likely open-source, so copy at will, bugs will be yours as well.
 */
package pt.cmg.jakartautils.text;

import java.text.MessageFormat;
import java.util.function.Supplier;
import org.apache.commons.lang3.StringUtils;
import org.owasp.encoder.Encode;

public class TextFormatter {

    private static final String EMPTY_STRING = "";

    /**
     * Formats a String by replacing each placeholder (in the form of {0...N}) with the arguments passed as
     * parameters. Underneath, it uses the {@link MessageFormat} class.
     */
    public static String formatMessage(String pattern, Object... arguments) {
        MessageFormat temp = new MessageFormat(pattern);
        return temp.format(arguments);
    }

    /**
     * Formats a String by replacing each placeholder (in the form of {0...N}) with the arguments passed as
     * parameters. Underneath, it uses the {@link MessageFormat} class.
     * Return a Supplier in order to only evaluate expressions passed as arguments only when its needed
     */
    public static Supplier<String> formatMessageToLazyLog(String pattern, Object... arguments) {
        MessageFormat temp = new MessageFormat(pattern);
        return () -> temp.format(arguments);
    }

    /**
     * Encodes a String by replacing special characters (new line / tab) with underscore to prevent Log Forging attacks
     */
    public static String encode(String message) {
        message = getFilteredString(message);
        if (message == null) {
            return null;
        }
        message = Encode.forHtml(message);
        return message;
    }

    /**
     * Encodes a String by replacing special characters (new line / tab) with underscore
     * to prevent URL Response Splitting attacks
     */
    public static String encodeForUri(String message) {
        message = getFilteredString(message);
        message = Encode.forUri(message);
        return message;
    }

    private static String getFilteredString(String message) {
        if (message == null) {
            return null;
        }
        message = message.replace("\n", "_").replace("\r", "_")
            .replace("\t", "_").replace("%0a", "_").replace("%0d", "_");
        return message;
    }

    /**
     * Utility function that will strip a String from Control characters (like \n or \r) and compacts it.
     * Optionally receives zero or more characters that will also be cleaned.
     *
     * @param target
     * @return
     */
    public static String cleanText(String target, char... charactersToDelete) {
        if (target == null || target.isEmpty()) {
            return EMPTY_STRING;
        }

        String currentText = target.replaceAll("\\p{Cntrl}", " ");

        for (char charToDelete : charactersToDelete) {
            currentText = currentText.replace(String.valueOf(charToDelete), " ");
        }

        return StringUtils.trim(currentText);
    }

}
