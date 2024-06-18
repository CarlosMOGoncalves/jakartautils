/**
 * Copyright (c) 2024 Carlos Gonçalves (https://www.linkedin.com/in/carlosmogoncalves/)
 * Likely open-source, so copy at will, bugs will be yours as well.
 */
package pt.cmg.jakartautils.errors;

import java.util.List;
import java.util.stream.Collectors;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;

/**
 * Allows for customisation of ConstraintViolationExceptions
 * 
 * @author Carlos Gonçalves
 */
public class ConstraintViolationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

    @Override
    public Response toResponse(ConstraintViolationException exception) {

        List<ErrorDTO> errors = exception.getConstraintViolations()
            .stream()
            .map(v -> ErrorDTO.fromErrorMessage(v.getMessage()))
            .collect(Collectors.toList());

        return Response.status(Response.Status.BAD_REQUEST)
            .entity(errors).type(MediaType.APPLICATION_JSON)
            .build();
    }
}
