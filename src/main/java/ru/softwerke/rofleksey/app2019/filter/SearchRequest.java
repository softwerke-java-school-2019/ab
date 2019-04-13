package ru.softwerke.rofleksey.app2019.filter;

import ru.softwerke.rofleksey.app2019.model.Model;
import ru.softwerke.rofleksey.app2019.storage.SearchQuery;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public abstract class SearchRequest<T extends Model> {
    private static final long DEFAULT_COUNT = 50, MAX_COUNT = 100;
    private long count;
    private long page;
    private List<Predicate<T>> filters;
    private Comparator<T> comparator;

    SearchRequest() {
        filters = new ArrayList<>();
        comparator = Comparator.comparing(Model::getId);
        count = DEFAULT_COUNT;
        page = 0;
    }

    public void withCount(String countString) throws MalformedSearchRequestException {
        long countTemp = SearchRequestUtils.parseNumber(countString, Long::valueOf);
        if (countTemp <= 0) {
            throw new MalformedSearchRequestException("positive count expected");
        }
        count = Math.min(MAX_COUNT, countTemp);
    }

    public void withPage(String pageString) throws MalformedSearchRequestException {
        long pageTemp = SearchRequestUtils.parseNumber(pageString, Long::valueOf);
        if (pageTemp < 0) {
            throw new MalformedSearchRequestException("non-negative offset expected");
        }
        page = pageTemp;
    }

    public void withFilterOptions(String filterType, String filterValue) throws MalformedSearchRequestException {
        validateFilterType(filterType);
        filters.add(getFilterFactories().get(filterType).createForInput(filterValue));
    }

    public void withOrderType(String orderType) throws MalformedSearchRequestException {
        validateOrderType(orderType);
        comparator = getComparators().get(orderType.substring(1));
        if (orderType.startsWith("-")) {
            comparator = comparator.reversed();
        }
    }

    private void validateFilterType(String filterType) throws MalformedSearchRequestException {
        if (!getFilterFactories().containsKey(filterType)) {
            throw new MalformedSearchRequestException("invalid filter type", filterType);
        }
    }

    private void validateOrderType(String orderType) throws MalformedSearchRequestException {
        if (!orderType.startsWith("+") && !orderType.startsWith("-")) {
            throw new MalformedSearchRequestException("order type should start with either '+' or '-'", orderType);
        }
        if (!getComparators().containsKey(orderType)) {
            throw new MalformedSearchRequestException("invalid order type", orderType);
        }
    }

    public SearchQuery<T> build() {
        return new SearchQuery<>(filters, comparator, count, page);
    }

    abstract Map<String, FilterFactory<T>> getFilterFactories();

    abstract Map<String, Comparator<T>> getComparators();

    interface FilterFactory<T> {
        Predicate<T> createForInput(String input) throws MalformedSearchRequestException;
    }
}
