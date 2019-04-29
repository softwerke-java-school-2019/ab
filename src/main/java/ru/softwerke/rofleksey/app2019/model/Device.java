package ru.softwerke.rofleksey.app2019.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import ru.softwerke.rofleksey.app2019.handlers.ColorDeserializer;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public class Device implements Model {
    public static final String DATE_FORMAT = "dd.MM.yyyy";
    private static final String ID_FIELD = "id";
    private static final String PRICE_FIELD = "price";
    private static final String MANUFACTURE_DATE_FIELD = "manufactureDate";
    private static final String TYPE_FIELD = "type";
    private static final String COLOR_NAME_FIELD = "colorName";
    private static final String COLOR_RGB_FIELD = "colorRgb";
    private static final String MANUFACTURER_FIELD = "manufacturer";
    private static final String MODEL_NAME_FIELD = "modelName";


    @JsonProperty(ID_FIELD)
    private long id;

    @JsonProperty(TYPE_FIELD)
    @NotNull(message = "type is null")
    private final DeviceType type;

    @JsonProperty(MANUFACTURER_FIELD)
    @NotBlank(message = "manufacturer is empty or null")
    private final String manufacturer;

    @JsonProperty(MODEL_NAME_FIELD)
    @NotBlank(message = "modelName is empty or null")
    private final String modelName;

    @JsonIgnore
    @NotNull(message = "color is null")
    @Valid
    private Color color;

    @JsonProperty(PRICE_FIELD)
    @NotNull(message = "price is null")
    private BigDecimal price;

    @JsonProperty(MANUFACTURE_DATE_FIELD)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT)
    @NotNull(message = "manufactureDate is null")
    private final LocalDate manufactureDate;

    @JsonIgnore
    private long dateLong;

    @JsonIgnore
    private double priceDouble;

    @JsonCreator
    public Device(
            @JsonProperty(value = PRICE_FIELD, required = true) BigDecimal price,
            @JsonProperty(value = TYPE_FIELD, required = true) DeviceType type,
            @JsonProperty(value = MANUFACTURE_DATE_FIELD, required = true) LocalDate manufactureDate,
            @JsonProperty(value = COLOR_NAME_FIELD, required = true) @JsonDeserialize(using = ColorDeserializer.class) Color color,
            @JsonProperty(value = MANUFACTURER_FIELD, required = true) String manufacturer,
            @JsonProperty(value = MODEL_NAME_FIELD, required = true) String modelName) {
        this.price = price;
        this.type = type;
        this.manufactureDate = manufactureDate;
        this.color = color;
        this.manufacturer = manufacturer;
        this.modelName = modelName;
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
    public void init() {
        priceDouble = price.doubleValue();
        dateLong = ModelUtils.localDateToLong(manufactureDate);
    }

    public BigDecimal getPrice() {
        return price;
    }

    public double getPriceDouble() {
        return priceDouble;
    }

    @JsonProperty(COLOR_NAME_FIELD)
    public String getColorName() {
        return color.getName();
    }

    public DeviceType getType() {
        return type;
    }

    @JsonProperty(COLOR_RGB_FIELD)
    public int getColorRGB() {
        return color.getRgb();
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public String getModelName() {
        return modelName;
    }

    public LocalDate getManufactureDate() {
        return manufactureDate;
    }

    public long getDateLong() {
        return dateLong;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Device device = (Device) o;
        return id == device.id &&
                Double.compare(device.priceDouble, priceDouble) == 0 &&
                type == device.type &&
                color.equals(device.color) &&
                manufacturer.equals(device.manufacturer) &&
                modelName.equals(device.modelName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, color, manufacturer, modelName, priceDouble);
    }

    @Override
    public String toString() {
        return "Device{" +
                "id=" + id +
                ", type=" + type +
                ", manufacturer='" + manufacturer + '\'' +
                ", modelName='" + modelName + '\'' +
                ", color=" + color +
                ", price=" + price +
                ", manufactureDate=" + manufactureDate +
                ", dateLong=" + dateLong +
                '}';
    }
}
