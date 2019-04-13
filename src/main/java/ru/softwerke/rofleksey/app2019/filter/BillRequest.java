package ru.softwerke.rofleksey.app2019.filter;

import ru.softwerke.rofleksey.app2019.model.Bill;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class BillRequest extends SearchRequest<Bill> {
    private static final String ID_CRITERIA = "id";
    private static final String TOTAL_PRICE_CRITERIA = "priceTotal";
    private static final String TOTAL_PRICE_EQUAL_CRITERIA = "priceTotalEqual";
    private static final String TOTAL_PRICE_MORE_CRITERIA = "priceTotalMore";
    private static final String TOTAL_PRICE_LESS_CRITERIA = "priceTotalLess";
    private static final String CLIENT_ID_CRITERIA = "clientId";
    private static final String WITH_DEVICE_ID_CRITERIA = "withDeviceId";
    private static final String DATE_CRITERIA = "date";

    private static final Map<String, FilterFactory<Bill>> filterFactories;
    private static final Map<String, Comparator<Bill>> comparators;

    static {
        filterFactories = new HashMap<>();
        comparators = new HashMap<>();
        filterFactories.put(TOTAL_PRICE_EQUAL_CRITERIA, p -> b -> b.getTotalPrice().toString().equals(p));
        filterFactories.put(TOTAL_PRICE_MORE_CRITERIA, p -> {
            double price = SearchRequestUtils.parseNumber(p, Double::valueOf);
            return bill -> Double.compare(bill.getTotalPriceDouble(), price) >= 0;
        });
        filterFactories.put(TOTAL_PRICE_LESS_CRITERIA, p -> {
            double price = SearchRequestUtils.parseNumber(p, Double::valueOf);
            return bill -> Double.compare(bill.getTotalPriceDouble(), price) >= 0;
        });
        filterFactories.put(CLIENT_ID_CRITERIA, id -> {
            long clientId = SearchRequestUtils.parseNumber(id, Long::valueOf);
            return bill -> bill.getClientId() == clientId;
        });
        filterFactories.put(WITH_DEVICE_ID_CRITERIA, id -> {
            long deviceId = SearchRequestUtils.parseNumber(id, Long::valueOf);
            return bill -> bill.containsDevice(deviceId);
        });
        filterFactories.put(DATE_CRITERIA, dateString -> {
            long date = SearchRequestUtils.parseNumber(dateString, Long::valueOf);
            return bill -> bill.getDate() == date;
        });
        comparators.put(ID_CRITERIA, Comparator.comparing(Bill::getId));
        comparators.put(TOTAL_PRICE_CRITERIA, Comparator.comparing(Bill::getTotalPriceDouble));
        comparators.put(CLIENT_ID_CRITERIA, Comparator.comparing(Bill::getClientId));
        comparators.put(DATE_CRITERIA, Comparator.comparing(Bill::getDate).thenComparing(Bill::getTime));
    }

    @Override
    Map<String, FilterFactory<Bill>> getFilterFactories() {
        return filterFactories;
    }

    @Override
    Map<String, Comparator<Bill>> getComparators() {
        return comparators;
    }
}
