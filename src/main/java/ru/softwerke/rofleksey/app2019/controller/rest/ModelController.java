package ru.softwerke.rofleksey.app2019.controller.rest;

import ru.softwerke.rofleksey.app2019.model.Model;
import ru.softwerke.rofleksey.app2019.service.DataService;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.util.List;

public abstract class ModelController<T extends Model> {
    DataService<T> service;

    public T createEntity(T t) {
        T nt = service.addEntity(t);
        QueryUtils.checkEmptyRequest(nt);
        return nt;
    }

    public T getEntityById(String idParam) {
        long id = QueryUtils.parseLongQueryParamMandatory(idParam, "id");
        T t = service.getEntityById(id);
        if (t == null) {
            Response response = Response
                    .status(Response.Status.NOT_FOUND)
                    .entity(Error.getInstance("entity doesn't exist", String.format("%s with id %d doesn't exist", getEntityName(), id)))
                    .build();
            throw new WebApplicationException(response);
        }
        return t;
    }

    abstract String getEntityName();

    public List<T> getList(String filterTerm,
                           String filterValue,
                           String orderTerm,
                           String countParam,
                           String offsetParam) {
        if (filterTerm != null) {
            QueryUtils.checkMandatoryWithAnother(filterValue, "filterValue", "filterBy");
            if (!service.isValidFilterTerm(filterTerm)) {
                Response response = Response
                        .status(Response.Status.BAD_REQUEST)
                        .entity(Error.getInstance("invalid term", String.format("%s dosn't support filter term '%s'", getEntityName(), filterTerm)))
                        .build();
                throw new WebApplicationException(response);
            }
        }
        if (orderTerm != null) {
            if (!service.isValidOrderTerm(orderTerm)) {
                Response response = Response
                        .status(Response.Status.BAD_REQUEST)
                        .entity(Error.getInstance("invalid term", String.format("%s dosn't support order term '%s'", getEntityName(), orderTerm)))
                        .build();
                throw new WebApplicationException(response);
            }
        }
        long count = QueryUtils.parseLongQueryParam(countParam, "count");
        long offset = QueryUtils.parseLongQueryParam(offsetParam, "offset");
        return service.search(filterTerm, filterValue, orderTerm, count, offset);
    }
}
