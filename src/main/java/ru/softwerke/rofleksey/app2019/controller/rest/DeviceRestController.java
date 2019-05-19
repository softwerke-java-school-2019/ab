package ru.softwerke.rofleksey.app2019.controller.rest;

import ru.softwerke.rofleksey.app2019.filter.DeviceRequest;
import ru.softwerke.rofleksey.app2019.filter.SearchRequest;
import ru.softwerke.rofleksey.app2019.model.Device;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@Path("/device")
public class DeviceRestController extends ModelController<Device> {
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Device createDevice(@Valid Device device) {
        addEntity(device);
        return device;
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
    String getEntityName() {
        return "device";
    }

    @Override
    SearchRequest<Device> getEmptySearchRequest() {
        return new DeviceRequest();
    }

    @Path("/type")
    public DeviceTypeSubController typeResource() {
        return new DeviceTypeSubController();
    }

    @Path("/color")
    public DeviceColorSubController colorResource() {
        return new DeviceColorSubController();
    }
}
