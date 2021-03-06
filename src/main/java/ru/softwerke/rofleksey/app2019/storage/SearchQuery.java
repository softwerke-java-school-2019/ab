package ru.softwerke.rofleksey.app2019.storage;

import ru.softwerke.rofleksey.app2019.model.Model;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * Holder of search arguments, such as filters, comparators, page number and page items count
 * Can be directly applied to stream of entities
 *
 * @param <T> entity type
 */
public class SearchQuery<T extends Model> {
    private final List<Predicate<T>> filters;
    private final List<Comparator<T>> comparators;
    private final long count, page;

    public SearchQuery(List<Predicate<T>> filter, List<Comparator<T>> comparators, long count, long page) {
        this.filters = filter;
        this.comparators = comparators;
        this.count = count;
        this.page = page;
    }

    /**
     * Applies search query to the stream of entities
     *
     * @param stream stream
     * @return filtered and sorted stream
     */
    Stream<T> apply(Stream<T> stream) {
        for (Predicate<T> predicate : filters) {
            stream = stream.filter(predicate);
        }
        Optional<Comparator<T>> foldedComparatorHolder = comparators.stream().reduce(Comparator::thenComparing);
        if (foldedComparatorHolder.isPresent()) {
            stream = stream.sorted(foldedComparatorHolder.get());
        }
        return stream.skip((page - 1) * count).limit(count);
    }
}
