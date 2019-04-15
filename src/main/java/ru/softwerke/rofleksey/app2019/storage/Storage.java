package ru.softwerke.rofleksey.app2019.storage;

import ru.softwerke.rofleksey.app2019.model.Model;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Model-independent storage
 *
 * @param <T> entity type
 */
public interface Storage<T extends Model> {
    /**
     * Add entity to storage
     *
     * @param entity entity
     * @return entity with id
     */
    T add(@NotNull T entity);

    /**
     * Execute search query
     *
     * @param query query
     * @return list of entities fitting target request criteria
     */
    List<T> executeQuery(SearchQuery<T> query);

    /**
     * Get entity by id
     *
     * @param id id
     * @return entity with target id or null if not found
     */
    T getById(long id);
}
