/**
 * Copyright (c) 2024 Carlos Gonçalves (https://www.linkedin.com/in/carlosmogoncalves/)
 * Likely open-source, so copy at will, bugs will be yours as well.
 */
package pt.cmg.jakartautils.errors;

import java.util.Map;
import java.util.stream.Collectors;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

/**
 * Allows for customisation of ConstraintViolationExceptions
 * 
 * @author Carlos Gonçalves
 */
public class ConstraintViolationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

    @Override
    public Response toResponse(ConstraintViolationException exception) {

        Map<String, String> errors = exception.getConstraintViolations().stream()
            .collect(Collectors.toMap(v -> getPropertyName(v.getPropertyPath()), ConstraintViolation::getMessage));

        return Response.status(Response.Status.BAD_REQUEST)
            .entity(errors).type(MediaType.APPLICATION_JSON)
            .build();
    }

    private String getPropertyName(Path path) {
        // The path has the form of com.package.class.property
        // Split the path by the dot (.) and take the last segment.
        String[] split = path.toString().split("\\.");
        return split[split.length - 1];
    }
}
