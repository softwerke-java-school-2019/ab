package ru.softwerke.rofleksey.app2019.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.StringUtils;

import javax.validation.constraints.NotBlank;
import java.util.Objects;

public class DeviceType implements NamedModel, Comparable<DeviceType> {
    private static final String ID_FIELD = "id";
    private static final String NAME_FIELD = "name";
    @JsonProperty(NAME_FIELD)
    @NotBlank(message = "name is empty or null")
    private final String name;
    @JsonIgnore
    private long id;


    @JsonCreator
    public DeviceType(
            @JsonProperty(value = NAME_FIELD, required = true) String name) {
        this.name = StringUtils.lowerCase(name);
    }

    public String getName() {
        return name;
    }

    @Override
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeviceType that = (DeviceType) o;
        return id == that.id &&
                name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "DeviceType{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public int compareTo(DeviceType o) {
        return name.compareTo(o.name);
    }
}
