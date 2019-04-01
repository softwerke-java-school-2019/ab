package ru.softwerke.rofleksey.app2019.controller.rest;

import ru.softwerke.rofleksey.app2019.model.Customer;
import ru.softwerke.rofleksey.app2019.service.CustomerDataService;
import ru.softwerke.rofleksey.app2019.service.CustomerDataServiceImpl;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/customer")
public class CustomerRestController {
    private CustomerDataService customerDataService = CustomerDataServiceImpl.getInstance();

    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String createCustomer(Customer customer) {
        return customerDataService.addEntity(customer);
    }

    @GET
    @Path("/id/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Customer getCustomerById(@PathParam("id") String id) {
        return customerDataService.getEntityById(Long.valueOf(id));
    }

    @GET
    @Path("/get")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Customer> getCustomers(@DefaultValue("") @QueryParam("filterBy") String filtering,
                                       @DefaultValue("") @QueryParam("filterValue") String filterValue,
                                       @DefaultValue("") @QueryParam("orderBy") String ordering,
                                       @DefaultValue("100") @QueryParam("count") long count,
                                       @DefaultValue("0") @QueryParam("offset") long offset) {
        return customerDataService.search(filtering, filterValue, ordering, count, offset);
    }
}
