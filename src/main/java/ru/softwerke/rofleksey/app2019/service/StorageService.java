package ru.softwerke.rofleksey.app2019.service;

import ru.softwerke.rofleksey.app2019.model.Model;
import ru.softwerke.rofleksey.app2019.storage.DataStorage;
import ru.softwerke.rofleksey.app2019.storage.SearchQuery;
import ru.softwerke.rofleksey.app2019.storage.Storage;

import java.util.List;

public class StorageService<T extends Model> implements DataService<T> {
    private final Storage<T> storage;

    public StorageService() {
        storage = new DataStorage<>();
    }

    public T addEntity(T entity) {
        return storage.add(entity);
    }

    public T getEntityById(long id) {
        return storage.getById(id);
    }

    public List<T> search(SearchQuery<T> query) {
        return storage.executeQuery(query);
    }
}
