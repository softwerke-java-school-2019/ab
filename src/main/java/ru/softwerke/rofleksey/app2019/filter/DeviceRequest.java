package ru.softwerke.rofleksey.app2019.filter;

import ru.softwerke.rofleksey.app2019.model.Device;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class DeviceRequest extends SearchRequest<Device> {
    private static final String ID_CRITERIA = "id";
    private static final String PRICE_CRITERIA = "price";
    private static final String PRICE_FROM_CRITERIA = "priceFrom";
    private static final String PRICE_TO_CRITERIA = "priceTo";
    private static final String TYPE_CRITERIA = "type";
    private static final String COLOR_NAME_CRITERIA = "colorName";
    private static final String COLOR_RGB_CRITERIA = "colorRGB";
    private static final String ISSUER_CRITERIA = "issuer";
    private static final String MODEL_CRITERIA = "model";

    private static final Map<String, FilterFactory<Device>> filterFactories;
    private static final Map<String, Comparator<Device>> comparators;

    static {
        filterFactories = new HashMap<>();
        comparators = new HashMap<>();
        filterFactories.put(PRICE_CRITERIA, p -> {
            double price = SearchRequestUtils.parseString(p, Double::valueOf);
            return device -> Double.compare(device.getPriceDouble(), price) == 0;
        });
        filterFactories.put(PRICE_FROM_CRITERIA, p -> {
            double price = SearchRequestUtils.parseString(p, Double::valueOf);
            return device -> Double.compare(device.getPriceDouble(), price) >= 0;
        });
        filterFactories.put(PRICE_TO_CRITERIA, p -> {
            double price = SearchRequestUtils.parseString(p, Double::valueOf);
            return device -> Double.compare(device.getPriceDouble(), price) <= 0;
        });
        filterFactories.put(TYPE_CRITERIA, type -> d -> d.getType().equals(type));
        filterFactories.put(COLOR_NAME_CRITERIA, name -> d -> d.getColorName().equals(name));
        filterFactories.put(COLOR_RGB_CRITERIA, color -> {
            int colorInt = SearchRequestUtils.parseString(color, Integer::valueOf);
            return device -> device.getColorRGB() == colorInt;
        });
        filterFactories.put(ISSUER_CRITERIA, is -> d -> d.getIssuer().equals(is));
        filterFactories.put(MODEL_CRITERIA, m -> d -> d.getModel().equals(m));
        comparators.put(ID_CRITERIA, Comparator.comparing(Device::getId));
        comparators.put(PRICE_CRITERIA, Comparator.comparing(Device::getPrice));
        comparators.put(TYPE_CRITERIA, Comparator.comparing(Device::getType));
        comparators.put(COLOR_NAME_CRITERIA, Comparator.comparing(Device::getColorName));
        comparators.put(COLOR_RGB_CRITERIA, Comparator.comparing(Device::getColorRGB));
        comparators.put(ISSUER_CRITERIA, Comparator.comparing(Device::getIssuer));
        comparators.put(MODEL_CRITERIA, Comparator.comparing(Device::getModel));
    }

    @Override
    Map<String, FilterFactory<Device>> getFilterFactories() {
        return filterFactories;
    }

    @Override
    Map<String, Comparator<Device>> getComparators() {
        return comparators;
    }
}
