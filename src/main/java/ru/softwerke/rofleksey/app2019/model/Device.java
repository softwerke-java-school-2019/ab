package ru.softwerke.rofleksey.app2019.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import ru.softwerke.rofleksey.app2019.handlers.ColorDeserializer;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Objects;

//TODO: implement date
public class Device implements Model {
    public static final String DATE_FORMAT = "dd.MM.yyyy";
    private static final String ID_FIELD = "id";
    private static final String PRICE_FIELD = "price";
    private static final String DATE_FIELD = "date";
    private static final String TYPE_FIELD = "type";
    private static final String COLOR_NAME_FIELD = "colorName";
    private static final String COLOR_RGB_FIELD = "colorRGB";
    private static final String ISSUER_FIELD = "issuer";
    private static final String MODEL_FIELD = "model";


    @JsonProperty(ID_FIELD)
    private long id;

    @JsonProperty(TYPE_FIELD)
    @NotNull(message = "type is null")
    private final DeviceType type;

    @JsonProperty(ISSUER_FIELD)
    @NotBlank(message = "issuer is empty or null")
    private final String issuer;

    @JsonProperty(MODEL_FIELD)
    @NotBlank(message = "model is empty or null")
    private final String model;

    @JsonIgnore
    @NotNull(message = "color is null")
    @Valid
    private Color color;

    @JsonProperty(PRICE_FIELD)
    @NotNull(message = "price is null")
    private BigDecimal price;

    @JsonIgnore
    private double priceDouble;

    @JsonCreator
    public Device(
            @JsonProperty(value = PRICE_FIELD, required = true) BigDecimal price,
            @JsonProperty(value = TYPE_FIELD, required = true) DeviceType type,
            @JsonProperty(value = COLOR_NAME_FIELD, required = true) @JsonDeserialize(using = ColorDeserializer.class) Color color,
            @JsonProperty(value = ISSUER_FIELD, required = true) String issuer,
            @JsonProperty(value = MODEL_FIELD, required = true) String model) {
        this.price = price;
        this.type = type;
        this.color = color;
        this.issuer = issuer;
        this.model = model;
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

    public String getIssuer() {
        return issuer;
    }

    public String getModel() {
        return model;
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
                issuer.equals(device.issuer) &&
                model.equals(device.model);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, color, issuer, model, priceDouble);
    }

    @Override
    public String toString() {
        return "Device{" +
                "id=" + id +
                ", type=" + type +
                ", color=" + color +
                ", issuer='" + issuer + '\'' +
                ", model='" + model + '\'' +
                ", price=" + price +
                '}';
    }
}
