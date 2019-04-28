package ru.softwerke.rofleksey.app2019.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Objects;


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

    @JsonProperty(COLOR_NAME_FIELD)
    @NotNull(message = "colorName is null")
    private final String colorName;

    @JsonProperty(COLOR_RGB_FIELD)
    @NotNull
    private final int colorRGB;

    @JsonProperty(ISSUER_FIELD)
    @NotNull(message = "issuer is null")
    private final String issuer;

    @JsonProperty(MODEL_FIELD)
    @NotNull(message = "model is null")
    private final String model;

    @JsonProperty(PRICE_FIELD)
    @NotNull(message = "price is null")
    private BigDecimal price;

    @JsonIgnore
    private double priceDouble;

    @JsonCreator
    public Device(
            @JsonProperty(value = PRICE_FIELD, required = true) BigDecimal price,
            @JsonProperty(value = TYPE_FIELD, required = true) DeviceType type,
            @JsonProperty(value = COLOR_NAME_FIELD, required = true) String colorName,
            @JsonProperty(value = COLOR_RGB_FIELD, required = true) int colorRGB,
            @JsonProperty(value = ISSUER_FIELD, required = true) String issuer,
            @JsonProperty(value = MODEL_FIELD, required = true) String model) {
        this.price = price;
        this.type = type;
        this.colorName = colorName;
        this.colorRGB = colorRGB;
        this.issuer = issuer;
        this.model = model;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Device that = (Device) o;
        return id == that.id &&
                Objects.equals(price, that.price) &&
                Objects.equals(colorName, that.colorName) &&
                Objects.equals(colorRGB, that.colorRGB) &&
                Objects.equals(issuer, that.issuer) &&
                Objects.equals(model, that.model);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, price, type, colorName, colorRGB, issuer, model);
    }

    @Override
    public String toString() {
        return "Device{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", colorName='" + colorName + '\'' +
                ", colorRGB=" + colorRGB +
                ", issuer='" + issuer + '\'' +
                ", model='" + model + '\'' +
                ", price=" + price +
                '}';
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

    public String getColorName() {
        return colorName;
    }

    public DeviceType getType() {
        return type;
    }

    public int getColorRGB() {
        return colorRGB;
    }

    public String getIssuer() {
        return issuer;
    }

    public String getModel() {
        return model;
    }
}
