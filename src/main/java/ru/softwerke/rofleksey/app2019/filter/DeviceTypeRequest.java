package ru.softwerke.rofleksey.app2019.filter;

import ru.softwerke.rofleksey.app2019.model.DeviceType;

import java.util.Collections;
import java.util.Comparator;
import java.util.Map;

public class DeviceTypeRequest extends SearchRequest<DeviceType> {
    private static final Map<String, FilterFactory<DeviceType>> filterFactories;
    private static final Map<String, Comparator<DeviceType>> comparators;

    static {
        filterFactories = Collections.emptyMap();
        comparators = Collections.emptyMap();
    }

    @Override
    Map<String, FilterFactory<DeviceType>> getFilterFactories() {
        return filterFactories;
    }

    @Override
    Map<String, Comparator<DeviceType>> getComparators() {
        return comparators;
    }
}
