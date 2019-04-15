package ru.softwerke.rofleksey.app2019.filter;

import org.apache.commons.lang3.StringUtils;
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
        long countTemp = SearchRequestUtils.parseString(countString, Long::valueOf);
        SearchRequestUtils.assertBool(() -> countTemp > 0, "positive count expected");
        count = Math.min(MAX_COUNT, countTemp);
    }

    public void withPage(String pageString) throws MalformedSearchRequestException {
        long pageTemp = SearchRequestUtils.parseString(pageString, Long::valueOf);
        SearchRequestUtils.assertBool(() -> pageTemp >= 0, "non-negative offset expected");
        page = pageTemp;
    }

    public void withFilterOptions(String filterType, String filterValue) throws MalformedSearchRequestException {
        SearchRequestUtils.assertBool(() -> getFilterFactories().containsKey(filterType), "invalid filter type", filterType);
        filters.add(getFilterFactories().get(filterType).createForInput(filterValue));
    }

    public void withOrderType(String orderType) throws MalformedSearchRequestException {
        boolean reverse = StringUtils.startsWith(orderType, "-");
        String actualOrderType = reverse ? StringUtils.substring(orderType, 1) : orderType;
        SearchRequestUtils.assertBool(() -> getComparators().containsKey(actualOrderType), "invalid order type", orderType);
        comparator = getComparators().get(actualOrderType);
        if (reverse) {
            comparator = comparator.reversed();
        }
    }

    public SearchQuery<T> buildQuery() {
        return new SearchQuery<>(filters, comparator, count, page);
    }

    abstract Map<String, FilterFactory<T>> getFilterFactories();

    abstract Map<String, Comparator<T>> getComparators();

    interface FilterFactory<T> {
        Predicate<T> createForInput(String input) throws MalformedSearchRequestException;
    }
}
