package ru.softwerke.rofleksey.app2019.controller.rest;

import org.apache.commons.lang3.StringUtils;
import ru.softwerke.rofleksey.app2019.filter.MalformedSearchRequestException;
import ru.softwerke.rofleksey.app2019.filter.SearchRequest;
import ru.softwerke.rofleksey.app2019.handlers.WebExceptionUtils;
import ru.softwerke.rofleksey.app2019.model.Model;
import ru.softwerke.rofleksey.app2019.storage.Storage;
import ru.softwerke.rofleksey.app2019.storage.StorageError;

import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.util.List;

abstract class ModelController<T extends Model> {
    private static final String PAGE_ITEMS = "pageItems";
    private static final String PAGE = "page";
    private static final String ORDER_TYPE = "orderBy";

    /**
     * Service providing underlying functionality
     */
    @Inject
    Storage<T> service;

    /**
     * Add entity to storage
     *
     * @param entity Entity
     * @return The same entity with id
     * @throws WebApplicationException if entity is null
     */
    void addEntity(T entity) throws WebApplicationException {
        QueryUtils.checkEmptyRequest(entity);
        validate(entity);
        entity.init();
        try {
            service.addEntity(entity);
        } catch (StorageError storageError) {
            throw WebExceptionUtils.getWebException(Response.Status.BAD_REQUEST, "storage add error", storageError.getMessage());
        }
    }

    /**
     * Get entity by id
     *
     * @param idParam String representation of id
     * @return entity with target id
     * @throws WebApplicationException If string is not a number or the entity doesn't exist
     */
    T getEntityById(String idParam) throws WebApplicationException {
        long id = QueryUtils.parseLongQueryParamMandatory(idParam, "id");
        T t = service.getEntityById(id);
        if (t == null) {
            throw WebExceptionUtils.getWebException(Response.Status.NOT_FOUND, "entity doesn't exist",
                    String.format("%s with id %d doesn't exist", getEntityName(), id));
        }
        return t;
    }

    /**
     * Search entities
     *
     * @param clientParams Search parameters
     * @return list of entities fitting the search criteria
     * @throws WebApplicationException if search parameters are malformed
     */
    List<T> search(MultivaluedMap<String, String> clientParams) throws WebApplicationException {
        try {
            SearchRequest<T> request = getEmptySearchRequest();
            for (String key : clientParams.keySet()) {
                String value = clientParams.getFirst(key);
                if (StringUtils.isBlank(value)) {
                    throw WebExceptionUtils.getWebException(Response.Status.BAD_REQUEST, "empty field",
                            String.format("field '%s' is empty", key));
                }
                switch (key) {
                    case PAGE_ITEMS: {
                        request.withPageItemsCount(value);
                        break;
                    }
                    case PAGE: {
                        request.withPage(value);
                        break;
                    }
                    case ORDER_TYPE: {
                        request.withOrderType(value);
                        break;
                    }
                    default: {
                        request.withFilterOptions(key, value);
                        break;
                    }
                }
            }
            List<T> list = service.search(request.buildQuery());
            if (list.isEmpty()) {
                throw WebExceptionUtils.getWebException(Response.Status.NOT_FOUND, "empty result", "result is empty");
            }
            return list;
        } catch (MalformedSearchRequestException e) {
            throw WebExceptionUtils.getWebException(Response.Status.BAD_REQUEST, "malformed request parameters", e.getMessage());
        }
    }

    void validate(T entity) throws WebApplicationException {

    }

    /**
     * @return Entity name
     */
    abstract String getEntityName();

    /**
     * @return Default search parameters
     */
    abstract SearchRequest<T> getEmptySearchRequest();
}
