package ru.softwerke.rofleksey.app2019.filter;

import org.apache.commons.lang3.StringUtils;
import ru.softwerke.rofleksey.app2019.model.Model;
import ru.softwerke.rofleksey.app2019.storage.SearchQuery;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

/**
 * Builder of SearchQuery
 *
 * @param <T>
 */
public abstract class SearchRequest<T extends Model> {
    private static final long DEFAULT_COUNT = 50, MAX_COUNT = 100;
    /**
     * Number of elements on the page
     */
    private long count;
    /**
     * Page number
     */
    private long page;
    /**
     * List of filters for target query
     */
    private List<Predicate<T>> filters;
    /**
     * List of comparators for target query
     */
    private List<Comparator<T>> comparators;


    /**
     * Constructs default SearchRequest, sorting elements by id
     */
    SearchRequest() {
        filters = new ArrayList<>();
        comparators = new ArrayList<>();
        count = DEFAULT_COUNT;
        page = 0;
    }

    /**
     * Attempts to set count from specified string
     *
     * @param countString string representation of count
     * @throws MalformedSearchRequestException if string doesn't describe valid count
     */
    public void withPageItemsCount(String countString) throws MalformedSearchRequestException {
        long countTemp = SearchRequestUtils.parseString(countString, Long::valueOf);
        SearchRequestUtils.assertBool(() -> countTemp > 0, "positive count expected");
        count = Math.min(MAX_COUNT, countTemp);
    }

    /**
     * Attempts to set page number from specified string
     *
     * @param pageString string representation of page number
     * @throws MalformedSearchRequestException if string doesn't describe valid page number
     */
    public void withPage(String pageString) throws MalformedSearchRequestException {
        long pageTemp = SearchRequestUtils.parseString(pageString, Long::valueOf);
        SearchRequestUtils.assertBool(() -> pageTemp >= 0, "non-negative page expected");
        page = pageTemp;
    }

    /**
     * Attempts to add filter to query from target arguments
     *
     * @param filterType filter type
     * @param filterValue filter value
     * @throws MalformedSearchRequestException if arguments doesn't describe valid filtration
     */
    public void withFilterOptions(String filterType, String filterValue) throws MalformedSearchRequestException {
        SearchRequestUtils.assertBool(() -> getFilterFactories().containsKey(filterType), "invalid filter type", filterType);
        filters.add(getFilterFactories().get(filterType).createForInput(filterValue));
    }

    /**
     * Attempts to set order type of query from string
     *
     * @param orderType string representation of order type
     * @throws MalformedSearchRequestException if arguments doesn't describe order type
     */
    public void withOrderType(String orderType) throws MalformedSearchRequestException {
        boolean reverse = StringUtils.startsWith(orderType, "-");
        String actualOrderType = reverse ? StringUtils.substring(orderType, 1) : orderType;
        SearchRequestUtils.assertBool(() -> getComparators().containsKey(actualOrderType), "invalid order type", orderType);
        Comparator<T> comparator = getComparators().get(actualOrderType);
        if (reverse) {
            comparator = comparator.reversed();
        }
        comparators.add(comparator);
    }

    /**
     * @return search query
     */
    public SearchQuery<T> buildQuery() {
        return new SearchQuery<>(filters, comparators, count, page);
    }

    /**
     * @return get filter factories for entity
     */
    abstract Map<String, FilterFactory<T>> getFilterFactories();

    /**
     * @return get filter comparators for entity
     */
    abstract Map<String, Comparator<T>> getComparators();

    /**
     * Predicate that may throw MalformedSearchRequestException
     *
     * @param <T> entity type
     */
    interface FilterFactory<T> {
        Predicate<T> createForInput(String input) throws MalformedSearchRequestException;
    }
}
