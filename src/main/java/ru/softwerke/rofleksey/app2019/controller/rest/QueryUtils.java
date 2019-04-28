package ru.softwerke.rofleksey.app2019.controller.rest;

import org.apache.commons.lang3.StringUtils;
import ru.softwerke.rofleksey.app2019.handlers.JSONErrorMessage;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

/**
 * Query assertions utility class
 */
class QueryUtils {

    /**
     * Assert that received object is not null, otherwise throw Bad Request
     *
     * @param o object to test
     * @throws WebApplicationException exception to be returned to client
     */
    static void checkEmptyRequest(Object o) throws WebApplicationException {
        if (o == null) {
            Response response = Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(JSONErrorMessage.create("empty", "Request body is empty"))
                    .build();
            throw new WebApplicationException(response);
        }
    }

    /**
     * Assert that parameter is set, otherwise throw Bad Request
     * @param param parameter value
     * @param paramName parameter name
     * @throws WebApplicationException exception to be returned to client
     */
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

    /**
     * Attempt to parse long from target parameter, throw Bad Request on error
     * @param param parameter value
     * @param paramName parameter name
     * @throws WebApplicationException exception to be returned to client
     */
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

    /**
     * Check if parameter is set and attempt to parse long from target parameter, throw Bad Request on error
     * @param param parameter value
     * @param paramName parameter name
     * @throws WebApplicationException exception to be returned to client
     */
    static long parseLongQueryParamMandatory(String param, String paramName) throws WebApplicationException {
        checkMandatory(param, paramName);
        return parseLongQueryParam(param, paramName);
    }
}
