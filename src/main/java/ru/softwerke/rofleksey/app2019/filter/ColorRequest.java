package ru.softwerke.rofleksey.app2019.filter;

import ru.softwerke.rofleksey.app2019.model.Color;

import java.util.Collections;
import java.util.Comparator;
import java.util.Map;

public class ColorRequest extends SearchRequest<Color> {
    private static final Map<String, FilterFactory<Color>> filterFactories;
    private static final Map<String, Comparator<Color>> comparators;

    static {
        filterFactories = Collections.emptyMap();
        comparators = Collections.emptyMap();
    }

    @Override
    Map<String, FilterFactory<Color>> getFilterFactories() {
        return filterFactories;
    }

    @Override
    Map<String, Comparator<Color>> getComparators() {
        return comparators;
    }
}
