package ru.softwerke.rofleksey.app2019.controller.rest;

import ru.softwerke.rofleksey.app2019.filter.DeviceTypeRequest;
import ru.softwerke.rofleksey.app2019.filter.SearchRequest;
import ru.softwerke.rofleksey.app2019.model.DeviceType;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import java.util.List;

public class DeviceTypeSubController extends ModelController<DeviceType> {
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public DeviceType addDeviceType(@Valid DeviceType type) {
        addEntity(type);
        return type;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<DeviceType> getDeviceTypes(@Context UriInfo ui) {
        return search(ui.getQueryParameters());
    }

    @Override
    String getEntityName() {
        return "deviceType";
    }

    @Override
    SearchRequest<DeviceType> getEmptySearchRequest() {
        return new DeviceTypeRequest();
    }
}
