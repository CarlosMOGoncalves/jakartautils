/**
 * Copyright (c) 2024 Carlos Gonçalves (https://www.linkedin.com/in/carlosmogoncalves/)
 * Likely open-source, so copy at will, bugs will be yours as well.
 */
package pt.cmg.jakartautils.errors;

public class ErrorDTO {

    public int code;
    public String description;

    public ErrorDTO(int code, String description) {
        this.code = code;
        this.description = description;
    }

}
