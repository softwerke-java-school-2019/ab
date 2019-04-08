package ru.softwerke.rofleksey.app2019.controller.rest;

import ru.softwerke.rofleksey.app2019.model.Bill;
import ru.softwerke.rofleksey.app2019.service.BillDataService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/bill")
public class BillRestController extends ModelController<Bill> {

    @Inject
    public BillRestController(BillDataService service) {
        this.service = service;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Bill createBill(Bill bill) {
        return createEntity(bill);
    }

    @GET
    @Path("/id")
    @Produces(MediaType.APPLICATION_JSON)
    public Bill getDeviceById(@QueryParam("id") String idParam) {
        return getEntityById(idParam);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Bill> getDevices(@QueryParam("filterBy") String filterTerm,
                                 @QueryParam("filterValue") String filterValue,
                                 @QueryParam("orderBy") String orderTerm,
                                 @DefaultValue("100") @QueryParam("count") String countParam,
                                 @DefaultValue("0") @QueryParam("offset") String offsetParam) {
        return getList(filterTerm, filterValue, orderTerm, countParam, offsetParam);
    }

    @Override
    String getEntityName() {
        return "bill";
    }
}
