/**
 * Copyright (c) 2024 Carlos Gonçalves (https://www.linkedin.com/in/carlosmogoncalves/)
 * Likely open-source, so copy at will, bugs will be yours as well.
 */
package pt.cmg.jakartautils.errors;

import javax.validation.Payload;
import org.apache.commons.lang3.StringUtils;

public class ErrorDTO implements Payload {

    public int code;
    public String description;

    public ErrorDTO(int code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * Shorthand to create an ErrorDTO from a String whose format is {number}-{errormessage}.
     * Useful for the ExceptionMapper.
     */
    public static ErrorDTO fromErrorMessage(String errorMessage) {
        if (errorMessage == null || errorMessage.isBlank()) {
            return new ErrorDTO(0, "Unspecified error message");
        }

        String[] errors = errorMessage.split("-");

        if (errors.length != 2) {
            return new ErrorDTO(0, errors[0]);
        }

        String errorText = "";
        try {
            int errorCode = Integer.valueOf(StringUtils.trim(errors[0]));
            errorText = StringUtils.trim(errors[1]);

            return new ErrorDTO(errorCode, errorText);
        } catch (NumberFormatException e) {
            return new ErrorDTO(0, errorText);
        }
    }

}
