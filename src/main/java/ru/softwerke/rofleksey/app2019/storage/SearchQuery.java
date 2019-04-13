package ru.softwerke.rofleksey.app2019.storage;

import ru.softwerke.rofleksey.app2019.model.Model;

import javax.validation.constraints.NotNull;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class SearchQuery<T extends Model> {
    private final List<Predicate<T>> filters;
    private final Comparator<T> comparator;
    private final long count, page;

    public SearchQuery(@NotNull List<Predicate<T>> filter, @NotNull Comparator<T> comparator, long count, long page) {
        this.filters = filter;
        this.comparator = comparator;
        this.count = count;
        this.page = page;
    }

    Stream<T> apply(Stream<T> stream) {
        for (Predicate<T> predicate : filters) {
            stream = stream.filter(predicate);
        }
        stream = stream.sorted(comparator);
        return stream.skip(page * count).limit(count);
    }
}
