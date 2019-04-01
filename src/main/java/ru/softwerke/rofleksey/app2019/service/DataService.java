package ru.softwerke.rofleksey.app2019.service;

import ru.softwerke.rofleksey.app2019.model.Model;

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

    public String addEntity(T t) {
        if (t != null) {
            list.add(t);
            idMap.put(t.getId(), t);
            return "Success";
        }
        return "Failed";
    }

    public T getEntityById(long id) {
        return idMap.get(id);
    }

    public List<T> search(String filtering, String filterValue, String ordering, long count, long offset) {
        Function<String, Predicate<T>> func = filters.get(filtering);
        Predicate<T> filter = func == null ? null : func.apply(filterValue);
        Comparator<T> sort = sorts.get(ordering);
        Stream<T> stream = list.stream();
        if (filter != null) {
            stream = stream.filter(filter);
        }
        if (sort != null) {
            stream = stream.sorted(sort);
        }
        return stream.skip(offset).limit(Math.min(count, MAX_COUNT)).collect(Collectors.toList());
    }
}
