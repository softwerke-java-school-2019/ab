package ru.softwerke.rofleksey.app2019.controller.rest;

import ru.softwerke.rofleksey.app2019.filter.BillRequest;
import ru.softwerke.rofleksey.app2019.filter.SearchRequest;
import ru.softwerke.rofleksey.app2019.handlers.WebExceptionUtils;
import ru.softwerke.rofleksey.app2019.model.Bill;
import ru.softwerke.rofleksey.app2019.model.BillItem;
import ru.softwerke.rofleksey.app2019.model.Customer;
import ru.softwerke.rofleksey.app2019.model.Device;
import ru.softwerke.rofleksey.app2019.storage.Storage;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@Path("/bill")
public class BillRestController extends ModelController<Bill> {
    @Inject
    private Storage<Customer> customerDataService;

    @Inject
    private Storage<Device> deviceDataService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Bill createBill(@Valid Bill bill) {
        addEntity(bill);
        return bill;
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

    @Override
    void validate(Bill bill) throws WebApplicationException {
        if (!customerDataService.hasEntity(bill.getCustomerId())) {
            throw WebExceptionUtils.getWebException(Response.Status.BAD_REQUEST, "invalid customerId",
                    String.format("customer with id %d not found", bill.getCustomerId()));
        }
        for (final BillItem item : bill.getItems()) {
            if (!deviceDataService.hasEntity(item.getDeviceId())) {
                throw WebExceptionUtils.getWebException(Response.Status.BAD_REQUEST, "invalid deviceId",
                        String.format("device with id %d not found", item.getDeviceId()));
            }
        }
    }
}
