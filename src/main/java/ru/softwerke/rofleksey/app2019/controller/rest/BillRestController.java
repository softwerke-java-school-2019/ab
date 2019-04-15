package ru.softwerke.rofleksey.app2019.controller.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.softwerke.rofleksey.app2019.filter.BillRequest;
import ru.softwerke.rofleksey.app2019.filter.SearchRequest;
import ru.softwerke.rofleksey.app2019.model.Bill;
import ru.softwerke.rofleksey.app2019.service.DataService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@Path("/bill")
public class BillRestController extends ModelController<Bill> {

    private static Logger logger = LoggerFactory.getLogger(BillRestController.class);

    @Inject
    public BillRestController(DataService<Bill> service) {
        this.service = service;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Bill createBill(Bill bill) {
        logger.info(bill.toString());
        return createEntity(bill);
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Bill getDeviceById(@PathParam("id") String idParam) {
        return getEntityById(idParam);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Bill> getDevices(@Context UriInfo ui) {
        return search(ui.getQueryParameters());
    }

    @Override
    String getEntityName() {
        return "bill";
    }

    @Override
    SearchRequest<Bill> getEmptySearchRequest() {
        return new BillRequest();
    }
}
