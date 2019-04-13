package ru.softwerke.rofleksey.app2019.storage;

import ru.softwerke.rofleksey.app2019.model.Model;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface Storage<T extends Model> {
    T add(@NotNull T entity);

    List<T> executeQuery(SearchQuery<T> query);

    T getById(long id);
}
