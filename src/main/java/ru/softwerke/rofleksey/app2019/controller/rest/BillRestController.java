package ru.softwerke.rofleksey.app2019.controller.rest;

import ru.softwerke.rofleksey.app2019.model.Bill;
import ru.softwerke.rofleksey.app2019.service.BillDataService;
import ru.softwerke.rofleksey.app2019.service.BillDataServiceImpl;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/bill")
public class BillRestController {
    private BillDataService billDataService = BillDataServiceImpl.getInstance();

    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String createCustomer(Bill bill) {
        return billDataService.addEntity(bill);
    }

    @GET
    @Path("/id/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Bill getDeviceById(@PathParam("id") String id) {
        return billDataService.getEntityById(Long.valueOf(id));
    }

    @GET
    @Path("/get")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Bill> getDevices(@DefaultValue("") @QueryParam("filterBy") String filtering,
                                 @DefaultValue("") @QueryParam("filterValue") String filterValue,
                                 @DefaultValue("") @QueryParam("orderBy") String ordering,
                                 @DefaultValue("100") @QueryParam("count") long count,
                                 @DefaultValue("0") @QueryParam("offset") long offset) {
        return billDataService.search(filtering, filterValue, ordering, count, offset);
    }
}
