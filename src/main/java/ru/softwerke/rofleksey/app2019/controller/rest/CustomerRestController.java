package ru.softwerke.rofleksey.app2019.controller.rest;

import ru.softwerke.rofleksey.app2019.model.Customer;
import ru.softwerke.rofleksey.app2019.service.CustomerDataService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/customer")
public class CustomerRestController extends ModelController<Customer> {
    @Inject
    public CustomerRestController(CustomerDataService service) {
        this.service = service;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Customer createCustomer(Customer customer) {
        return createEntity(customer);
    }

    @GET
    @Path("/id")
    @Produces(MediaType.APPLICATION_JSON)
    public Customer getCustomerById(@QueryParam("id") String idParam) {
        return getEntityById(idParam);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Customer> getCustomers(@QueryParam("filterBy") String filterTerm,
                                       @QueryParam("filterValue") String filterValue,
                                       @QueryParam("orderBy") String orderTerm,
                                       @DefaultValue("100") @QueryParam("count") String countParam,
                                       @DefaultValue("0") @QueryParam("offset") String offsetParam) {
        return getList(filterTerm, filterValue, orderTerm, countParam, offsetParam);
    }

    @Override
    String getEntityName() {
        return "customer";
    }
}
