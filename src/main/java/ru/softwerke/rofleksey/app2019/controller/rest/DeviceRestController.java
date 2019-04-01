package ru.softwerke.rofleksey.app2019.controller.rest;

import ru.softwerke.rofleksey.app2019.model.Device;
import ru.softwerke.rofleksey.app2019.service.DeviceDataService;
import ru.softwerke.rofleksey.app2019.service.DeviceDataServiceImpl;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/device")
public class DeviceRestController {
    private DeviceDataService deviceDataService = DeviceDataServiceImpl.getInstance();

    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String createCustomer(Device device) {
        return deviceDataService.addEntity(device);
    }

    @GET
    @Path("/id/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Device getDeviceById(@PathParam("id") String id) {
        return deviceDataService.getEntityById(Long.valueOf(id));
    }

    @GET
    @Path("/get")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Device> getDevices(@DefaultValue("") @QueryParam("filterBy") String filtering,
                                   @DefaultValue("") @QueryParam("filterValue") String filterValue,
                                   @DefaultValue("") @QueryParam("orderBy") String ordering,
                                   @DefaultValue("100") @QueryParam("count") long count,
                                   @DefaultValue("0") @QueryParam("offset") long offset) {
        return deviceDataService.search(filtering, filterValue, ordering, count, offset);
    }
}
