package ru.softwerke.rofleksey.app2019.filter;

import ru.softwerke.rofleksey.app2019.model.Customer;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class CustomerRequest extends SearchRequest<Customer> {
    private static final String ID_CRITERIA = "id";
    private static final String FIRST_NAME_CRITERIA = "firstName";
    private static final String MIDDLE_NAME_CRITERIA = "middleName";
    private static final String LAST_NAME_CRITERIA = "lastName";
    private static final String FULL_NAME_CRITERIA = "fullName";
    private static final String BIRTH_DATE_CRITERIA = "birthdate";

    private static final Map<String, FilterFactory<Customer>> filterFactories;
    private static final Map<String, Comparator<Customer>> comparators;

    private static final DateTimeFormatter format = DateTimeFormatter.ofPattern(Customer.DATE_FORMAT);

    static {
        Map<String, FilterFactory<Customer>> filterFactoriesTemp = new HashMap<>();
        Map<String, Comparator<Customer>> comparatorTemp = new HashMap<>();
        filterFactoriesTemp.put(FIRST_NAME_CRITERIA, name -> c -> c.getFirstName().equals(name));
        filterFactoriesTemp.put(MIDDLE_NAME_CRITERIA, name -> c -> c.getMiddleName().equals(name));
        filterFactoriesTemp.put(LAST_NAME_CRITERIA, name -> c -> c.getLastName().equals(name));
        filterFactoriesTemp.put(FULL_NAME_CRITERIA, name -> c -> c.getFullName().equals(name));
        filterFactoriesTemp.put(BIRTH_DATE_CRITERIA, date -> {
            LocalDate tmpDate = SearchRequestUtils.parseString(date, it -> LocalDate.parse(it, format));
            long tmpLong = tmpDate.atStartOfDay().toInstant(ZoneOffset.UTC).toEpochMilli();
            return customer -> customer.getBirthDateLong() == tmpLong;
        });
        comparatorTemp.put(ID_CRITERIA, Comparator.comparing(Customer::getId));
        comparatorTemp.put(FIRST_NAME_CRITERIA, Comparator.comparing(Customer::getFirstName));
        comparatorTemp.put(MIDDLE_NAME_CRITERIA, Comparator.comparing(Customer::getMiddleName));
        comparatorTemp.put(LAST_NAME_CRITERIA, Comparator.comparing(Customer::getLastName));
        comparatorTemp.put(FULL_NAME_CRITERIA, Comparator.comparing(Customer::getFullName));
        comparatorTemp.put(BIRTH_DATE_CRITERIA, Comparator.comparing(Customer::getBirthDateLong));

        filterFactories = Collections.unmodifiableMap(filterFactoriesTemp);
        comparators = Collections.unmodifiableMap(comparatorTemp);
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
