package ru.softwerke.rofleksey.app2019.filter;

import ru.softwerke.rofleksey.app2019.model.Bill;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class BillRequest extends SearchRequest<Bill> {
    private static final String ID_CRITERIA = "id";
    private static final String TOTAL_PRICE_CRITERIA = "priceTotal";
    private static final String TOTAL_PRICE_FROM_CRITERIA = "priceTotalFrom";
    private static final String TOTAL_PRICE_TO_CRITERIA = "priceTotalTo";
    private static final String CLIENT_ID_CRITERIA = "clientId";
    private static final String WITH_DEVICE_ID_CRITERIA = "withDeviceId";
    private static final String DATE_CRITERIA = "date";
    private static final String DATE_TIME_CRITERIA = "dateTime";
    private static final String DATE_TIME_FROM_CRITERIA = "dateTimeFrom";
    private static final String DATE_TIME_TO_CRITERIA = "dateTimeTo";

    private static final Map<String, FilterFactory<Bill>> filterFactories;
    private static final Map<String, Comparator<Bill>> comparators;

    private static final DateTimeFormatter format = DateTimeFormatter.ofPattern(Bill.DATE_FORMAT);

    static {
        filterFactories = new HashMap<>();
        comparators = new HashMap<>();
        filterFactories.put(TOTAL_PRICE_CRITERIA, p -> {
            double price = SearchRequestUtils.parseString(p, Double::valueOf);
            return bill -> Double.compare(bill.getTotalPriceDouble(), price) == 0;
        });
        filterFactories.put(TOTAL_PRICE_FROM_CRITERIA, p -> {
            double price = SearchRequestUtils.parseString(p, Double::valueOf);
            return bill -> Double.compare(bill.getTotalPriceDouble(), price) >= 0;
        });
        filterFactories.put(TOTAL_PRICE_TO_CRITERIA, p -> {
            double price = SearchRequestUtils.parseString(p, Double::valueOf);
            return bill -> Double.compare(bill.getTotalPriceDouble(), price) <= 0;
        });
        filterFactories.put(CLIENT_ID_CRITERIA, id -> {
            long clientId = SearchRequestUtils.parseString(id, Long::valueOf);
            return bill -> bill.getClientId() == clientId;
        });
        filterFactories.put(WITH_DEVICE_ID_CRITERIA, id -> {
            long deviceId = SearchRequestUtils.parseString(id, Long::valueOf);
            return bill -> bill.containsDevice(deviceId);
        });
        filterFactories.put(DATE_CRITERIA, dateString -> {
            LocalDateTime date = SearchRequestUtils.parseString(dateString, it -> LocalDateTime.parse(it, format));
            long dateLong = date.toInstant(ZoneOffset.UTC).with(LocalTime.MIDNIGHT).toEpochMilli();
            return bill -> bill.getDateLong() == dateLong;
        });
        filterFactories.put(DATE_TIME_CRITERIA, dateString -> {
            LocalDateTime date = SearchRequestUtils.parseString(dateString, it -> LocalDateTime.parse(it, format));
            long dateLong = date.toInstant(ZoneOffset.UTC).toEpochMilli();
            return bill -> bill.getDateLong() == dateLong;
        });
        filterFactories.put(DATE_TIME_FROM_CRITERIA, dateString -> {
            LocalDateTime date = SearchRequestUtils.parseString(dateString, it -> LocalDateTime.parse(it, format));
            long dateLong = date.toInstant(ZoneOffset.UTC).toEpochMilli();
            return bill -> bill.getDateLong() >= dateLong;
        });
        filterFactories.put(DATE_TIME_TO_CRITERIA, dateString -> {
            LocalDateTime date = SearchRequestUtils.parseString(dateString, it -> LocalDateTime.parse(it, format));
            long dateLong = date.toInstant(ZoneOffset.UTC).toEpochMilli();
            return bill -> bill.getDateLong() <= dateLong;
        });

        comparators.put(ID_CRITERIA, Comparator.comparing(Bill::getId));
        comparators.put(TOTAL_PRICE_CRITERIA, Comparator.comparing(Bill::getTotalPriceDouble));
        comparators.put(CLIENT_ID_CRITERIA, Comparator.comparing(Bill::getClientId));
        comparators.put(DATE_CRITERIA, Comparator.comparing(Bill::getDateLong));
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
