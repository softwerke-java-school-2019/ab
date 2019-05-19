package ru.softwerke.rofleksey.app2019.service;

import ru.softwerke.rofleksey.app2019.model.NamedModel;
import ru.softwerke.rofleksey.app2019.storage.StorageError;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * StorageService for models with name
 * Additionally stores map of names to entities for fast retrieval
 */
public class StringMapService<T extends NamedModel> extends StorageService<T> {
    private final Map<String, T> nameMap;
    private final boolean allowOverwrite;

    StringMapService(boolean allowOverwrite) {
        super();
        this.allowOverwrite = allowOverwrite;
        nameMap = new ConcurrentHashMap<>();
    }

    @Override
    public void addEntity(T entity) throws StorageError {
        if (!allowOverwrite && nameMap.containsKey(entity.getName())) {
            throw new StorageError(entityTypeName() + " with name " + entity.getName() + " already exists");
        }
        nameMap.put(entity.getName(), entity);
        super.addEntity(entity);
    }

    public T getByName(String name) {
        return nameMap.get(name);
    }
}
