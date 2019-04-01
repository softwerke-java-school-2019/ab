package ru.softwerke.rofleksey.app2019.service;

import ru.softwerke.rofleksey.app2019.model.Bill;

import java.util.Comparator;
import java.util.function.Predicate;

public class BillDataServiceImpl extends BillDataService {
    private static final String ID_CRITERIA = "id";
    private static final String TOTAL_PRICE_CRITERIA = "price_total";
    private static final String TOTAL_PRICE_EQUAL_CRITERIA = "price_total_equal";
    private static final String TOTAL_PRICE_MORE_CRITERIA = "price_total_more";
    private static final String TOTAL_PRICE_LESS_CRITERIA = "price_total_less";
    private static final String CLIENT_ID_CRITERIA = "client_id";
    private static final String WITH_DEVICE_ID_CRITERIA = "with_device_id";
    private static final String DATE_CRITERIA = "date";

    private static BillDataServiceImpl INSTANCE;

    private BillDataServiceImpl() {
        super();
        filters.put(TOTAL_PRICE_EQUAL_CRITERIA, p -> b -> b.getTotalPrice().toString().equals(p));
        filters.put(TOTAL_PRICE_MORE_CRITERIA, p -> new Predicate<Bill>() {
            double price = Double.valueOf(p);

            @Override
            public boolean test(Bill bill) {
                return Double.compare(bill.getTotalPriceDouble(), price) >= 0;
            }
        });
        filters.put(TOTAL_PRICE_LESS_CRITERIA, p -> new Predicate<Bill>() {
            double price = Double.valueOf(p);

            @Override
            public boolean test(Bill bill) {
                return Double.compare(bill.getTotalPriceDouble(), price) <= 0;
            }
        });
        filters.put(CLIENT_ID_CRITERIA, id -> new Predicate<Bill>() {
            long clientId = Long.valueOf(id);

            @Override
            public boolean test(Bill bill) {
                return bill.getClientId() == clientId;
            }
        });
        filters.put(WITH_DEVICE_ID_CRITERIA, id -> new Predicate<Bill>() {
            long deviceId = Long.valueOf(id);

            @Override
            public boolean test(Bill bill) {
                return bill.containsDevice(deviceId);
            }
        });
        filters.put(DATE_CRITERIA, dateString -> new Predicate<Bill>() {
            long date = Long.valueOf(dateString);

            @Override
            public boolean test(Bill bill) {
                return bill.getDate() == date;
            }
        });
        sorts.put(ID_CRITERIA, Comparator.comparing(Bill::getId));
        sorts.put(TOTAL_PRICE_CRITERIA, Comparator.comparing(Bill::getTotalPriceDouble));
        sorts.put(CLIENT_ID_CRITERIA, Comparator.comparing(Bill::getClientId));
        sorts.put(DATE_CRITERIA, Comparator.comparing(Bill::getDate).thenComparing(Bill::getTime));
    }

    public static BillDataServiceImpl getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new BillDataServiceImpl();
        }
        return INSTANCE;
    }
}
