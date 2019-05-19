package ru.softwerke.rofleksey.app2019.handlers;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import ru.softwerke.rofleksey.app2019.model.Color;
import ru.softwerke.rofleksey.app2019.model.DeviceType;

import javax.annotation.Priority;
import javax.inject.Singleton;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

//TODO: formats

/**
 * Handler of JSON syntax errors. Automatically responds with Bad Request on error
 */
@Provider
@Priority(1)
@Produces(MediaType.APPLICATION_JSON)
@Singleton
public class InvalidFormatExceptionHandler implements ExceptionMapper<InvalidFormatException> {
    private static final String MESSAGE_DETAILED = "'%s' - %s", MESSAGE_SIMPLE = "'%s' has invalid format";
    private final Set<Class> showDetailsMap;

    public InvalidFormatExceptionHandler() {
        Set<Class> tempSet = new HashSet<>();
        tempSet.add(Color.class);
        tempSet.add(DeviceType.class);
        showDetailsMap = Collections.unmodifiableSet(tempSet);
    }

    @Override
    public Response toResponse(InvalidFormatException ex) {
        ex.clearLocation();
        JSONErrorMessage message = JSONErrorMessage.create("invalid format",
                String.format(showDetailsMap.contains(ex.getTargetType()) ? MESSAGE_DETAILED : MESSAGE_SIMPLE,
                        ex.getValue(), ex.getOriginalMessage()));
        return Response.status(Response.Status.BAD_REQUEST).entity(message).build();
    }
}
