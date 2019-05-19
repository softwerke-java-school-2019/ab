package ru.softwerke.rofleksey.app2019.controller.rest;

import ru.softwerke.rofleksey.app2019.filter.ColorRequest;
import ru.softwerke.rofleksey.app2019.filter.SearchRequest;
import ru.softwerke.rofleksey.app2019.model.Color;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import java.util.List;


public class DeviceColorSubController extends ModelController<Color> {
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Color addColor(@Valid Color color) {
        addEntity(color);
        return color;
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
