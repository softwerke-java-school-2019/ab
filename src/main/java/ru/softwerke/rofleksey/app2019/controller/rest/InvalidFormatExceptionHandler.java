package ru.softwerke.rofleksey.app2019.controller.rest;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;

import javax.annotation.Priority;
import javax.inject.Singleton;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Handler of JSON syntax errors. Automatically responds with Bad Request on error
 */

@Provider
@Priority(1)
@Produces(MediaType.APPLICATION_JSON)
@Singleton
public class InvalidFormatExceptionHandler implements ExceptionMapper<InvalidFormatException> {

    @Override
    public Response toResponse(InvalidFormatException ex) {
        JSONErrorMessage message = JSONErrorMessage.create("invalid format",
                String.format("string '%s' has invalid format", ex.getValue()));
        return Response.status(Response.Status.BAD_REQUEST).entity(message).build();
    }
}
