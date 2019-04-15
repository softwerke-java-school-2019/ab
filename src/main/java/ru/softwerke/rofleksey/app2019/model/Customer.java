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
    private static final String ID_FIELD = "id";
    private static final String FIRST_NAME_FIELD = "firstName";
    public static final String DATE_FORMAT = "dd-MM-yyyy";
    private static final String LAST_NAME_FIELD = "lastName";
    private static final String BIRTH_DATE_FIELD = "birthDate";
    private static final String PATRONYMIC_FIELD = "patronymic";


    @JsonProperty(ID_FIELD)
    private long id;

    @JsonProperty(FIRST_NAME_FIELD)
    private final String firstName;
    @JsonProperty(PATRONYMIC_FIELD)
    private final String patronymic;

    @JsonProperty(LAST_NAME_FIELD)
    private final String lastName;

    @JsonProperty(BIRTH_DATE_FIELD)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT)
    private final LocalDate birthDate;

    @JsonIgnore
    private final long birthDateLong;

    @JsonIgnore
    private final String fullName;

    @JsonCreator
    public Customer(@NotNull @JsonProperty(value = FIRST_NAME_FIELD, required = true) String firstName,
                    @NotNull @JsonProperty(value = PATRONYMIC_FIELD, required = true) String patronymic,
                    @NotNull @JsonProperty(value = LAST_NAME_FIELD, required = true) String lastName,
                    @NotNull @JsonProperty(value = BIRTH_DATE_FIELD, required = true) LocalDate birthDate) {
        this.firstName = firstName;
        this.patronymic = patronymic;
        this.lastName = lastName;
        this.fullName = firstName + " " + patronymic + " " + lastName;
        this.birthDate = birthDate;
        this.birthDateLong = birthDate.atStartOfDay().toInstant(ZoneOffset.UTC).toEpochMilli();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer that = (Customer) o;
        return id == that.id &&
                Objects.equals(firstName, that.firstName) &&
                Objects.equals(patronymic, that.patronymic) &&
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
                ", patronymic='" + patronymic + '\'' +
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

    public String getFirstName() {
        return firstName;
    }

    public String getPatronymic() {
        return patronymic;
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
        return Objects.hash(id, firstName, patronymic, lastName, birthDate);
    }
}
