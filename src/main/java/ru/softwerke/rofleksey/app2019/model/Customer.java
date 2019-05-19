package ru.softwerke.rofleksey.app2019.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDate;
import java.util.Objects;

//TODO: 'fullName', 'birthDateLong' fields are still allowed in JSON serialization (possible bug in jackson???)

public class Customer implements Model {
    public static final String DATE_FORMAT = "dd.MM.yyyy";
    private static final String ID_FIELD = "id";
    private static final String FIRST_NAME_FIELD = "firstName";
    private static final String LAST_NAME_FIELD = "lastName";
    private static final String BIRTH_DATE_FIELD = "birthdate";
    private static final String MIDDLE_NAME_FIELD = "middleName";

    @JsonProperty(BIRTH_DATE_FIELD)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT)
    @NotNull(message = "birthDate is null")
    @Past(message = "birthday in the future is not allowed")
    private final LocalDate birthDate;

    @JsonProperty(FIRST_NAME_FIELD)
    @NotBlank(message = "firstName is empty or null")
    private final String firstName;

    @JsonProperty(MIDDLE_NAME_FIELD)
    @NotBlank(message = "middleName is empty or null")
    private final String middleName;

    @JsonProperty(LAST_NAME_FIELD)
    @NotBlank(message = "lastName is empty or null")
    private final String lastName;
    @JsonIgnore
    private long id;

    @JsonIgnore
    private long birthDateLong;

    @JsonIgnore
    private String fullName;

    @JsonCreator
    public Customer(@JsonProperty(value = FIRST_NAME_FIELD, required = true) String firstName,
                    @JsonProperty(value = MIDDLE_NAME_FIELD, required = true) String middleName,
                    @JsonProperty(value = LAST_NAME_FIELD, required = true) String lastName,
                    @JsonProperty(value = BIRTH_DATE_FIELD, required = true) LocalDate birthDate) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.birthDate = birthDate;
    }

    @JsonProperty(ID_FIELD)
    public long getId() {
        return id;
    }

    @Override
    @JsonIgnore
    public void setId(long id) {
        this.id = id;
    }

    @Override
    public void init() {
        fullName = firstName + " " + middleName + " " + lastName;
        birthDateLong = ModelUtils.localDateToLong(birthDate);
    }

    public String getFirstName() {
        return firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFullName() {
        return fullName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    @JsonIgnore
    public long getBirthDateLong() {
        return birthDateLong;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return id == customer.id &&
                birthDateLong == customer.birthDateLong &&
                firstName.equals(customer.firstName) &&
                middleName.equals(customer.middleName) &&
                lastName.equals(customer.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, middleName, lastName, birthDateLong);
    }
}
