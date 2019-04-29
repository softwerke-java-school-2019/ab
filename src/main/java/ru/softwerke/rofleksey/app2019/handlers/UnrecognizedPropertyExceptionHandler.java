package ru.softwerke.rofleksey.app2019.handlers;

import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;

import javax.annotation.Priority;
import javax.inject.Singleton;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;


/**
 * Handler of JSON unrecognized field errors. Automatically responds with Bad Request on error
 */
@Provider
@Priority(1)
@Produces(MediaType.APPLICATION_JSON)
@Singleton
public class UnrecognizedPropertyExceptionHandler implements ExceptionMapper<UnrecognizedPropertyException> {

    @Override
    public Response toResponse(UnrecognizedPropertyException exception) {
        exception.clearLocation();
        JSONErrorMessage message = JSONErrorMessage.create("unrecognized property",
                String.format("Unrecognized property '%s'", exception.getPropertyName()));
        return Response.status(Response.Status.BAD_REQUEST).entity(message).build();
    }

}