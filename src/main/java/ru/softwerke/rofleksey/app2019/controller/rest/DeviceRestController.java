package ru.softwerke.rofleksey.app2019.controller.rest;

import ru.softwerke.rofleksey.app2019.filter.DeviceRequest;
import ru.softwerke.rofleksey.app2019.filter.SearchRequest;
import ru.softwerke.rofleksey.app2019.handlers.JSONErrorMessage;
import ru.softwerke.rofleksey.app2019.model.Color;
import ru.softwerke.rofleksey.app2019.model.Device;
import ru.softwerke.rofleksey.app2019.service.ColorService;
import ru.softwerke.rofleksey.app2019.service.DataService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@Path("/device")
public class DeviceRestController extends ModelController<Device> {
    private final ColorService colorService;

    @Inject
    public DeviceRestController(DataService<Device> service, ColorService colorService) {
        this.service = service;
        this.colorService = colorService;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Device createDevice(@Valid Device device) {
        return createEntity(device);
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Device getDeviceById(@PathParam("id") String idParam) {
        return getEntityById(idParam);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Device> getDevices(@Context UriInfo ui) {
        return search(ui.getQueryParameters());
    }

    @Override
    JSONErrorMessage validate(Device device) {
        Color color = colorService.getByName(device.getColorName());
        if (color == null) {
            return JSONErrorMessage.create("invalid color name", String.format("color '%s' doesn't exist", device.getColorName()));
        }
        device.setColor(color);
        return null;
    }

    @Override
    String getEntityName() {
        return "device";
    }

    @Override
    SearchRequest<Device> getEmptySearchRequest() {
        return new DeviceRequest();
    }
}
