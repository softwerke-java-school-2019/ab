package ru.softwerke.rofleksey.app2019.service;

import ru.softwerke.rofleksey.app2019.model.Customer;

import java.util.Comparator;
import java.util.function.Predicate;

public class CustomerDataServiceImpl extends CustomerDataService {
    private static final String ID_CRITERIA = "id";
    private static final String FIRST_NAME_CRITERIA = "first_name";
    private static final String MIDDLE_NAME_CRITERIA = "middle_name";
    private static final String LAST_NAME_CRITERIA = "last_name";
    private static final String FULL_NAME_CRITERIA = "full_name";
    private static final String BIRTH_DATE_CRITERIA = "birth_date";

    private CustomerDataServiceImpl() {
        super();
        filters.put(FIRST_NAME_CRITERIA, name -> c -> c.getFirstName().equals(name));
        filters.put(MIDDLE_NAME_CRITERIA, name -> c -> c.getMiddleName().equals(name));
        filters.put(LAST_NAME_CRITERIA, name -> c -> c.getLastName().equals(name));
        filters.put(FULL_NAME_CRITERIA, name -> c -> c.getFullName().equals(name));
        filters.put(BIRTH_DATE_CRITERIA, date -> new Predicate<Customer>() {
            long tmpDate = Long.valueOf(date);

            @Override
            public boolean test(Customer customer) {
                return customer.getBirthDate() == tmpDate;
            }
        });
        sorts.put(ID_CRITERIA, Comparator.comparing(Customer::getId));
        sorts.put(FIRST_NAME_CRITERIA, Comparator.comparing(Customer::getFirstName));
        sorts.put(MIDDLE_NAME_CRITERIA, Comparator.comparing(Customer::getMiddleName));
        sorts.put(LAST_NAME_CRITERIA, Comparator.comparing(Customer::getLastName));
        sorts.put(FULL_NAME_CRITERIA, Comparator.comparing(Customer::getFullName));
        sorts.put(BIRTH_DATE_CRITERIA, Comparator.comparing(Customer::getBirthDate));
    }

    public static CustomerDataServiceImpl getInstance() {
        return new CustomerDataServiceImpl();
    }
}
