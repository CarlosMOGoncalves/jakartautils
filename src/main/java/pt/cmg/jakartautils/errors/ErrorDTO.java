/**
 * Copyright (c) 2024 Carlos Gonçalves (https://www.linkedin.com/in/carlosmogoncalves/)
 * Likely open-source, so copy at will, bugs will be yours as well.
 */
package pt.cmg.jakartautils.errors;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class ErrorDTO {

    public List<Map<String, Integer>> errors = null;

    public ErrorDTO() {
        this.errors = new ArrayList<>();
    }

    public ErrorDTO(List<Map<String, Integer>> errors) {
        this.errors = errors;
    }

    public ErrorDTO(int code) {
        addError(code);
    }

    public void addError(int code) {
        if (errors == null) {
            errors = new ArrayList<>();
        }

        errors.add(Collections.singletonMap("code", code));
    }

}
