/**
 * Copyright (c) 2024 Carlos Gonçalves (https://www.linkedin.com/in/carlosmogoncalves/)
 * Likely open-source, so copy at will, bugs will be yours as well.
 */
package pt.cmg.jakartautils.errors;

import java.util.Objects;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import jakarta.validation.Payload;

@Schema(description = "Generic Error in the system with a message and code", example = """
    {
        "code": 1001,
        "description": "Email cannot be null or empty"
    }
    """)
public class ErrorDTO implements Payload {

    public static final ErrorDTO UNSPECIFIED_ERROR = new ErrorDTO(0, "Unspecified error message");

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
            return UNSPECIFIED_ERROR;
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

    @Override
    public int hashCode() {
        return Objects.hash(code, description);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        ErrorDTO other = (ErrorDTO) obj;
        return code == other.code && Objects.equals(description, other.description);
    }

}
