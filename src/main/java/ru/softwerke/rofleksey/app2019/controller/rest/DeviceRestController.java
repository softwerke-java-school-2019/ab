package ru.softwerke.rofleksey.app2019.controller.rest;

import ru.softwerke.rofleksey.app2019.model.Device;
import ru.softwerke.rofleksey.app2019.service.DeviceDataService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/device")
public class DeviceRestController extends ModelController<Device> {

    @Inject
    public DeviceRestController(DeviceDataService service) {
        this.service = service;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Device createDevice(Device device) {
        return createEntity(device);
    }

    @GET
    @Path("/id")
    @Produces(MediaType.APPLICATION_JSON)
    public Device getDeviceById(@QueryParam("id") String idParam) {
        return getEntityById(idParam);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Device> getDevices(@QueryParam("filterBy") String filterTerm,
                                   @QueryParam("filterValue") String filterValue,
                                   @QueryParam("orderBy") String orderTerm,
                                   @DefaultValue("100") @QueryParam("count") String countParam,
                                   @DefaultValue("0") @QueryParam("offset") String offsetParam) {
        return getList(filterTerm, filterValue, orderTerm, countParam, offsetParam);
    }

    @Override
    String getEntityName() {
        return "device";
    }
}
