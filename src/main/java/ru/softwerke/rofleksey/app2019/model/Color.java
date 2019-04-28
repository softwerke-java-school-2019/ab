package ru.softwerke.rofleksey.app2019.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import java.util.Objects;

public class Color implements Model {
    private static final String ID_FIELD = "id";
    private static final String NAME_FIELD = "name";
    private static final String RGB_FIELD = "rgb";
    @JsonProperty(NAME_FIELD)
    @NotBlank(message = "name is empty or null")
    private final String name;
    @JsonProperty(RGB_FIELD)
    private final int rgb;
    @JsonProperty(ID_FIELD)
    private long id;

    @JsonCreator
    public Color(
            @JsonProperty(value = NAME_FIELD, required = true) String name,
            @JsonProperty(value = RGB_FIELD, required = true) int rgb) {
        this.name = name;
        this.rgb = rgb;
    }

    public Color(String name, int red, int green, int blue) {
        this(name, ((red & 0x0ff) << 16) | ((green & 0x0ff) << 8) | (blue & 0x0ff));
    }

    public String getName() {
        return name;
    }

    public int getRgb() {
        return rgb;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Color color = (Color) o;
        return id == color.id &&
                name.equals(color.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "Color{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", rgb=" + rgb +
                '}';
    }
}
