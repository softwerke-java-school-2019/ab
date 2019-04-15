package ru.softwerke.rofleksey.app2019.filter;

import ru.softwerke.rofleksey.app2019.model.Customer;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class CustomerRequest extends SearchRequest<Customer> {
    private static final String ID_CRITERIA = "id";
    private static final String FIRST_NAME_CRITERIA = "firstName";
    private static final String MIDDLE_NAME_CRITERIA = "middleName";
    private static final String LAST_NAME_CRITERIA = "lastName";
    private static final String FULL_NAME_CRITERIA = "fullName";
    private static final String BIRTH_DATE_CRITERIA = "birthDate";

    private static final Map<String, FilterFactory<Customer>> filterFactories;
    private static final Map<String, Comparator<Customer>> comparators;

    private static final DateTimeFormatter format = DateTimeFormatter.ofPattern(Customer.DATE_FORMAT);

    static {
        filterFactories = new HashMap<>();
        comparators = new HashMap<>();
        filterFactories.put(FIRST_NAME_CRITERIA, name -> c -> c.getFirstName().equals(name));
        filterFactories.put(MIDDLE_NAME_CRITERIA, name -> c -> c.getPatronymic().equals(name));
        filterFactories.put(LAST_NAME_CRITERIA, name -> c -> c.getLastName().equals(name));
        filterFactories.put(FULL_NAME_CRITERIA, name -> c -> c.getFullName().equals(name));
        filterFactories.put(BIRTH_DATE_CRITERIA, date -> {
            LocalDate tmpDate = SearchRequestUtils.parseString(date, it -> LocalDate.parse(it, format));
            long tmpLong = tmpDate.atStartOfDay().toInstant(ZoneOffset.UTC).toEpochMilli();
            return customer -> customer.getBirthDateLong() == tmpLong;
        });
        comparators.put(ID_CRITERIA, Comparator.comparing(Customer::getId));
        comparators.put(FIRST_NAME_CRITERIA, Comparator.comparing(Customer::getFirstName));
        comparators.put(MIDDLE_NAME_CRITERIA, Comparator.comparing(Customer::getPatronymic));
        comparators.put(LAST_NAME_CRITERIA, Comparator.comparing(Customer::getLastName));
        comparators.put(FULL_NAME_CRITERIA, Comparator.comparing(Customer::getFullName));
        comparators.put(BIRTH_DATE_CRITERIA, Comparator.comparing(Customer::getBirthDateLong));
    }

    @Override
    Map<String, FilterFactory<Customer>> getFilterFactories() {
        return filterFactories;
    }

    @Override
    Map<String, Comparator<Customer>> getComparators() {
        return comparators;
    }
}
