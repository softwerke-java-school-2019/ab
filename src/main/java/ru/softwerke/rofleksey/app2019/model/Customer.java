package ru.softwerke.rofleksey.app2019.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Objects;

public class Customer implements Model {
    private static final String ID_FIELD = "id";
    private static final String FIRST_NAME_FIELD = "firstName";
    private static final String MIDDLE_NAME_FIELD = "middleName";
    private static final String LAST_NAME_FIELD = "lastName";
    private static final String BIRTH_DATE_FIELD = "birthDate";


    @JsonProperty(ID_FIELD)
    private long id;

    @JsonProperty(FIRST_NAME_FIELD)
    private final String firstName;

    @JsonProperty(MIDDLE_NAME_FIELD)
    private final String middleName;

    @JsonProperty(LAST_NAME_FIELD)
    private final String lastName;

    @JsonProperty(BIRTH_DATE_FIELD)
    private final long birthDate;

    @JsonIgnore
    private final String fullName;

    @JsonIgnore
    private final Date birthDateObj;

    @JsonCreator
    public Customer(@NotNull @JsonProperty(value = FIRST_NAME_FIELD, required = true) String firstName,
                    @NotNull @JsonProperty(value = MIDDLE_NAME_FIELD, required = true) String middleName,
                    @NotNull @JsonProperty(value = LAST_NAME_FIELD, required = true) String lastName,
                    @NotNull @JsonProperty(value = BIRTH_DATE_FIELD, required = true) long birthDate) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.fullName = firstName + " " + middleName + " " + lastName;
        this.birthDate = birthDate;
        this.birthDateObj = new Date(birthDate);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer that = (Customer) o;
        return id == that.id &&
                Objects.equals(firstName, that.firstName) &&
                Objects.equals(middleName, that.middleName) &&
                Objects.equals(lastName, that.lastName) &&
                Objects.equals(birthDate, that.birthDate);
    }

    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
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

    public long getBirthDate() {
        return birthDate;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, middleName, lastName, birthDate);
    }
}
