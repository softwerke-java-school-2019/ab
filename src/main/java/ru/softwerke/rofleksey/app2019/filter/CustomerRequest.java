package ru.softwerke.rofleksey.app2019.filter;

import ru.softwerke.rofleksey.app2019.model.Customer;

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

    static {
        filterFactories = new HashMap<>();
        comparators = new HashMap<>();
        filterFactories.put(FIRST_NAME_CRITERIA, name -> c -> c.getFirstName().equals(name));
        filterFactories.put(MIDDLE_NAME_CRITERIA, name -> c -> c.getMiddleName().equals(name));
        filterFactories.put(LAST_NAME_CRITERIA, name -> c -> c.getLastName().equals(name));
        filterFactories.put(FULL_NAME_CRITERIA, name -> c -> c.getFullName().equals(name));
        filterFactories.put(BIRTH_DATE_CRITERIA, date -> {
            long tmpDate = SearchRequestUtils.parseNumber(date, Long::valueOf);
            return customer -> customer.getBirthDate() == tmpDate;
        });
        comparators.put(ID_CRITERIA, Comparator.comparing(Customer::getId));
        comparators.put(FIRST_NAME_CRITERIA, Comparator.comparing(Customer::getFirstName));
        comparators.put(MIDDLE_NAME_CRITERIA, Comparator.comparing(Customer::getMiddleName));
        comparators.put(LAST_NAME_CRITERIA, Comparator.comparing(Customer::getLastName));
        comparators.put(FULL_NAME_CRITERIA, Comparator.comparing(Customer::getFullName));
        comparators.put(BIRTH_DATE_CRITERIA, Comparator.comparing(Customer::getBirthDate));
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
