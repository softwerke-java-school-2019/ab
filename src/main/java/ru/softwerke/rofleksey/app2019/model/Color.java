package ru.softwerke.rofleksey.app2019.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.StringUtils;

import javax.validation.constraints.NotBlank;
import java.util.Objects;

public class Color implements NamedModel {
    private static final String ID_FIELD = "id";
    private static final String NAME_FIELD = "name";
    private static final String RGB_FIELD = "rgb";

    @JsonIgnore
    private long id;

    @JsonProperty(NAME_FIELD)
    @NotBlank(message = "name is empty or null")
    private final String name;

    @JsonProperty(RGB_FIELD)
    private final int rgb;


    @JsonCreator
    public Color(
            @JsonProperty(value = NAME_FIELD, required = true) String name,
            @JsonProperty(value = RGB_FIELD, required = true) int rgb) {
        this.name = StringUtils.lowerCase(name);
        this.rgb = rgb;
    }

    public static Color fromRGB(String name, int red, int green, int blue) {
        return new Color(name, ((red & 0x0ff) << 16) | ((green & 0x0ff) << 8) | (blue & 0x0ff));
    }

    public String getName() {
        return name;
    }

    public int getRgb() {
        return rgb;
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
