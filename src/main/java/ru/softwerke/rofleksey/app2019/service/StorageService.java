package ru.softwerke.rofleksey.app2019.service;

import ru.softwerke.rofleksey.app2019.model.Model;
import ru.softwerke.rofleksey.app2019.storage.DataStorage;
import ru.softwerke.rofleksey.app2019.storage.SearchQuery;
import ru.softwerke.rofleksey.app2019.storage.Storage;
import ru.softwerke.rofleksey.app2019.storage.StorageError;

import java.util.List;

public class StorageService<T extends Model> implements Storage<T> {
    private final Storage<T> storage;

    public StorageService() {
        storage = new DataStorage<>();
    }

    public void addEntity(T entity) throws StorageError {
        storage.addEntity(entity);
    }

    public T getEntityById(long id) {
        return storage.getEntityById(id);
    }

    public List<T> search(SearchQuery<T> query) {
        return storage.search(query);
    }
}
