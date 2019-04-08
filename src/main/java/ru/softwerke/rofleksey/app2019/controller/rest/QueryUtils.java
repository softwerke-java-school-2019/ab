package ru.softwerke.rofleksey.app2019.controller.rest;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

public class QueryUtils {

    static void checkEmptyRequest(Object o) {
        if (o == null) {
            Response response = Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(Error.getInstance("empty", "Request body is empty"))
                    .build();
            throw new WebApplicationException(response);
        }
    }

    static void checkMandatoryWithAnother(String param, String paramName, String anotherName) {
        checkMandatory(param, paramName, "parameter '%s' is mandatory if parameter '" + anotherName + "' is set");
    }

    static void checkMandatory(String param, String paramName) {
        checkMandatory(param, paramName, "parameter '%s' is mandatory");
    }

    static void checkMandatory(String param, String paramName, String customMessage) {
        if (param == null) {
            throw new WebApplicationException(
                    Response.status(Response.Status.BAD_REQUEST)
                            .entity(Error.getInstance("missing mandatory query parameter", String.format(customMessage, paramName)))
                            .build()
            );
        }
    }

    static long parseLongQueryParam(String param, String paramName) {
        long number;
        try {
            number = Long.valueOf(param);
        } catch (NumberFormatException e) {
            throw new WebApplicationException(
                    Response.status(Response.Status.BAD_REQUEST)
                            .entity(Error.getInstance("invalid query parameter format", String.format("parameter '%s' is not a number", paramName)))
                            .build()
            );
        }
        return number;
    }

    static long parseLongQueryParamMandatory(String param, String paramName) {
        checkMandatory(param, paramName);
        return parseLongQueryParam(param, paramName);
    }
}
