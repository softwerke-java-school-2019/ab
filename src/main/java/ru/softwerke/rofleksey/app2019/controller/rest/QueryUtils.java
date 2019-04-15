package ru.softwerke.rofleksey.app2019.controller.rest;

import org.apache.commons.lang3.StringUtils;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

class QueryUtils {

    static void checkEmptyRequest(Object o) throws WebApplicationException {
        if (o == null) {
            Response response = Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(JSONErrorMessage.create("empty", "Request body is empty"))
                    .build();
            throw new WebApplicationException(response);
        }
    }

    private static void checkMandatory(String param, String paramName) throws WebApplicationException {
        checkMandatory(param, paramName, "parameter '%s' is mandatory");
    }

    private static void checkMandatory(String param, String paramName, String customMessage) throws WebApplicationException {
        if (StringUtils.isBlank(param)) {
            throw new WebApplicationException(
                    Response.status(Response.Status.BAD_REQUEST)
                            .entity(JSONErrorMessage.create("missing mandatory query parameter",
                                    String.format(customMessage, paramName)))
                            .build()
            );
        }
    }

    private static long parseLongQueryParam(String param, String paramName) throws WebApplicationException {
        long number;
        try {
            number = Long.valueOf(param);
        } catch (NumberFormatException e) {
            throw new WebApplicationException(
                    Response.status(Response.Status.BAD_REQUEST)
                            .entity(JSONErrorMessage.create("invalid query parameter format",
                                    String.format("parameter '%s' is not a number", paramName)))
                            .build()
            );
        }
        return number;
    }

    static long parseLongQueryParamMandatory(String param, String paramName) throws WebApplicationException {
        checkMandatory(param, paramName);
        return parseLongQueryParam(param, paramName);
    }
}
