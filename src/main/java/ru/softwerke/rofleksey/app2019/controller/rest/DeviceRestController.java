package ru.softwerke.rofleksey.app2019.controller.rest;

import ru.softwerke.rofleksey.app2019.filter.ColorRequest;
import ru.softwerke.rofleksey.app2019.filter.DeviceRequest;
import ru.softwerke.rofleksey.app2019.filter.DeviceTypeRequest;
import ru.softwerke.rofleksey.app2019.filter.SearchRequest;
import ru.softwerke.rofleksey.app2019.model.Color;
import ru.softwerke.rofleksey.app2019.model.Device;
import ru.softwerke.rofleksey.app2019.model.DeviceType;
import ru.softwerke.rofleksey.app2019.storage.Storage;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@Path("/device")
public class DeviceRestController extends ModelController<Device> {

    @Inject
    private Storage<DeviceType> deviceTypeService;

    @Inject
    private Storage<Color> colorService;

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
        return new DeviceTypeSubController(deviceTypeService);
    }

    @Path("/color")
    public DeviceColorSubController colorResource() {
        return new DeviceColorSubController(colorService);
    }

    public static class DeviceTypeSubController extends ModelController<DeviceType> {
        DeviceTypeSubController(Storage<DeviceType> service) {
            this.service = service;
        }

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

    public static class DeviceColorSubController extends ModelController<Color> {
        DeviceColorSubController(Storage<Color> service) {
            this.service = service;
        }

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
}
