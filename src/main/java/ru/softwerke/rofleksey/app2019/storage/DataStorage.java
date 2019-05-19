package ru.softwerke.rofleksey.app2019.storage;

import ru.softwerke.rofleksey.app2019.model.Model;

import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class DataStorage<T extends Model> implements Storage<T> {
    private final Collection<T> dataList = new ConcurrentLinkedQueue<>();
    private final Map<Long, T> idMap = new ConcurrentHashMap<>();
    private final AtomicLong idProducer = new AtomicLong(0);

    @Override
    public void addEntity(@NotNull T entity) {
        long id = idProducer.getAndIncrement();
        entity.setId(id);
        dataList.add(entity);
        idMap.put(id, entity);
    }

    @Override
    public List<T> search(SearchQuery<T> query) {
        return query.apply(dataList.stream()).collect(Collectors.toList());
    }

    @Override
    public T getEntityById(long id) {
        return idMap.get(id);
    }

    @Override
    public String entityTypeName() {
        return "entity";
    }
}
