package ru.softwerke.rofleksey.app2019.controller.rest;

import com.fasterxml.jackson.core.JsonProcessingException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class JsonProcessingExceptionMapper implements ExceptionMapper<JsonProcessingException> {
    @Override
    public Response toResponse(JsonProcessingException exception) {
        JSONErrorMessage message = JSONErrorMessage.create("invalid json", exception.getOriginalMessage());
        return Response.status(Response.Status.BAD_REQUEST).entity(message).build();
    }
}