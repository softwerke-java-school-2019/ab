package ru.softwerke.rofleksey.app2019.handlers;

import javax.annotation.Priority;
import javax.inject.Singleton;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.Iterator;
import java.util.Set;

/**
 * Handler of null fields and other validation errors. Automatically responds with Bad Request on error
 */
@Provider
@Priority(1)
@Produces(MediaType.APPLICATION_JSON)
@Singleton
public class ConstraintViolationExceptionHandler implements ExceptionMapper<ConstraintViolationException> {

    @Override
    public Response toResponse(final ConstraintViolationException exception) {
        JSONErrorMessage message = JSONErrorMessage.create("validation error",
                extractCause(exception.getConstraintViolations()));
        return Response.status(Response.Status.BAD_REQUEST).entity(message).build();
    }

    private String extractCause(Set<ConstraintViolation<?>> violationSet) {
        Iterator<ConstraintViolation<?>> iterator = violationSet.iterator();
        ConstraintViolation<?> violation = iterator.next();
        return violation.getMessage();
    }
}
