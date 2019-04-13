package ru.softwerke.rofleksey.app2019.controller.rest;

import ru.softwerke.rofleksey.app2019.filter.MalformedSearchRequestException;
import ru.softwerke.rofleksey.app2019.filter.SearchRequest;
import ru.softwerke.rofleksey.app2019.model.Model;
import ru.softwerke.rofleksey.app2019.service.DataService;
import ru.softwerke.rofleksey.app2019.storage.SearchQuery;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Map;

abstract class ModelController<T extends Model> {
    private static final String COUNT = "count";
    private static final String PAGE = "page";
    private static final String ORDER_TYPE = "orderType";

    DataService<T> service;

    T createEntity(T t) throws WebApplicationException {
        QueryUtils.checkEmptyRequest(t);
        return service.addEntity(t);
    }

    T getEntityById(String idParam) throws WebApplicationException {
        long id = QueryUtils.parseLongQueryParamMandatory(idParam, "id");
        T t = service.getEntityById(id);
        if (t == null) {
            Response response = Response
                    .status(Response.Status.NOT_FOUND)
                    .entity(JSONErrorMessage.create("entity doesn't exist", String.format("%s with id %d doesn't exist", getEntityName(), id)))
                    .build();
            throw new WebApplicationException(response);
        }
        return t;
    }

    List<T> search(MultivaluedMap<String, String> clientParams) throws WebApplicationException {
        try {
            SearchRequest<T> request = getEmptySearchRequest();
            executeIfPresent(request::withCount, clientParams, COUNT);
            executeIfPresent(request::withPage, clientParams, PAGE);
            executeIfPresent(request::withOrderType, clientParams, ORDER_TYPE);
            for (Map.Entry<String, List<String>> entry : clientParams.entrySet()) {
                request.withFilterOptions(entry.getKey(), entry.getValue().get(0));
            }
            SearchQuery<T> query = request.build();
            return service.search(query);
        } catch (MalformedSearchRequestException e) {
            Response response = Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(JSONErrorMessage.create("malformed request parameters", e.getMessage()))
                    .build();
            throw new WebApplicationException(response);
        }
    }

    private void executeIfPresent(ConsumerWithException action, MultivaluedMap<String, String> queryParams, String what) throws MalformedSearchRequestException {
        List<String> list = queryParams.get(what);
        if (list != null) {
            action.accept(list.get(0));
            queryParams.remove(what);
        }
    }

    abstract String getEntityName();

    abstract SearchRequest<T> getEmptySearchRequest();

    private interface ConsumerWithException {
        void accept(String s) throws MalformedSearchRequestException;
    }
}
