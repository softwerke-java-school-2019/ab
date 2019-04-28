package ru.softwerke.rofleksey.app2019.controller.rest;

import ru.softwerke.rofleksey.app2019.filter.ColorRequest;
import ru.softwerke.rofleksey.app2019.filter.SearchRequest;
import ru.softwerke.rofleksey.app2019.model.Color;
import ru.softwerke.rofleksey.app2019.service.ColorService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@Path("/color")
public class ColorController extends ModelController<Color> {

    @Inject
    public ColorController(ColorService service) {
        this.service = service;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Color addColor(@Valid Color color) {
        return createEntity(color);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Color> getColors(@Context UriInfo ui) {
        return search(ui.getQueryParameters());
    }

    @Override
    String getEntityName() {
        return "color";
    }

    @Override
    SearchRequest<Color> getEmptySearchRequest() {
        return new ColorRequest();
    }
}
