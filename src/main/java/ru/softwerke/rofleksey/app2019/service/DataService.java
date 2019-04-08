package ru.softwerke.rofleksey.app2019.service;

import ru.softwerke.rofleksey.app2019.model.Model;

import javax.ws.rs.NotFoundException;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class DataService<T extends Model> {
    private static final long MAX_COUNT = 100;
    Map<String, Function<String, Predicate<T>>> filters;
    Map<String, Comparator<T>> sorts;
    private ArrayList<T> list;
    private Map<Long, T> idMap;

    DataService() {
        list = new ArrayList<>();
        filters = new HashMap<>(10, 1);
        sorts = new HashMap<>(10, 1);
        idMap = new HashMap<>();
    }

    public T addEntity(T t) {
        if (t != null) {
            list.add(t);
            idMap.put(t.getId(), t);
        }
        return t;
    }

    public T getEntityById(long id) throws NotFoundException {
        return idMap.get(id);
    }

    public List<T> search(String filterTerm, String filterValue, String orderTerm, long count, long offset) {
        Function<String, Predicate<T>> func = filters.get(filterTerm);
        Predicate<T> filter = func == null ? null : func.apply(filterValue);
        Comparator<T> sort = sorts.get(orderTerm);
        Stream<T> stream = list.stream();
        if (filter != null) {
            stream = stream.filter(filter);
        }
        if (sort != null) {
            stream = stream.sorted(sort);
        }
        return stream.skip(offset).limit(Math.min(count, MAX_COUNT)).collect(Collectors.toList());
    }

    public boolean isValidFilterTerm(String term) {
        return filters.containsKey(term);
    }

    public boolean isValidOrderTerm(String term) {
        return sorts.containsKey(term);
    }
}
