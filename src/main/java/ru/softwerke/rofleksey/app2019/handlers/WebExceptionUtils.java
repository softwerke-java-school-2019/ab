package ru.softwerke.rofleksey.app2019.handlers;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

public class WebExceptionUtils {
    /**
     * Constructs WebApplicationException using target status, error type and error message
     *
     * @param status  status code
     * @param type    error type
     * @param message error message
     * @return constructed error
     */
    public static WebApplicationException getWebException(Response.Status status, String type, String message) {
        Response response = Response
                .status(status)
                .entity(JSONErrorMessage.create(type, message))
                .build();
        return new WebApplicationException(response);
    }
}
