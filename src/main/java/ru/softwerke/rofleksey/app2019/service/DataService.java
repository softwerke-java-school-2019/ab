package ru.softwerke.rofleksey.app2019.service;

import ru.softwerke.rofleksey.app2019.model.Model;
import ru.softwerke.rofleksey.app2019.storage.SearchQuery;

import java.util.List;

public interface DataService<T extends Model> {
    T addEntity(T entity);

    T getEntityById(long id);

    List<T> search(SearchQuery<T> query);
}
