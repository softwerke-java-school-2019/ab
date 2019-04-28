package ru.softwerke.rofleksey.app2019.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Objects;


public class Customer implements Model {
    public static final String DATE_FORMAT = "dd.MM.yyyy";
    private static final String ID_FIELD = "id";
    private static final String FIRST_NAME_FIELD = "firstName";
    private static final String LAST_NAME_FIELD = "lastName";
    private static final String BIRTH_DATE_FIELD = "birthdate";
    private static final String MIDDLE_NAME_FIELD = "middleName";


    @JsonProperty(ID_FIELD)
    private long id;

    @JsonProperty(FIRST_NAME_FIELD)
    @NotNull(message = "firstName is null")
    private final String firstName;

    @JsonProperty(MIDDLE_NAME_FIELD)
    @NotNull(message = "middleName is null")
    private final String middleName;

    @JsonProperty(LAST_NAME_FIELD)
    @NotNull(message = "lastName is null")
    private final String lastName;

    @JsonProperty(BIRTH_DATE_FIELD)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT)
    @NotNull(message = "birthDate is null")
    private final LocalDate birthDate;

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
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthDate=" + birthDate +
                ", birthDateLong=" + birthDateLong +
                ", fullName='" + fullName + '\'' +
                '}';
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    @Override
    public void init() {
        fullName = firstName + " " + middleName + " " + lastName;
        birthDateLong = birthDate.atStartOfDay().toInstant(ZoneOffset.UTC).toEpochMilli();
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

    public long getBirthDateLong() {
        return birthDateLong;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, middleName, lastName, birthDate);
    }
}
