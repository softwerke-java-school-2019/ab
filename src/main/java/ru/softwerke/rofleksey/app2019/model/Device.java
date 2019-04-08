package ru.softwerke.rofleksey.app2019.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

public class Device implements Model {
    private static final String ID_FIELD = "id";
    private static final String PRICE_FIELD = "price";
    private static final String TYPE_FIELD = "type";
    private static final String COLOR_NAME_FIELD = "color_name";
    private static final String COLOR_RGB_FIELD = "color_rgb";
    private static final String ISSUER_FIELD = "issuer";
    private static final String MODEL_FIELD = "model";

    private static AtomicLong idCounter = new AtomicLong(0);


    @JsonProperty(ID_FIELD)
    private final long id;
    @JsonProperty(TYPE_FIELD)
    private final String type;
    @JsonProperty(COLOR_NAME_FIELD)
    private final String colorName;
    @JsonProperty(COLOR_RGB_FIELD)
    private final int colorRGB;
    @JsonProperty(ISSUER_FIELD)
    private final String issuer;
    @JsonProperty(MODEL_FIELD)
    private final String model;
    @JsonProperty(PRICE_FIELD)
    private BigDecimal price;

    @JsonCreator
    public Device(
            @NotNull @JsonProperty(value = PRICE_FIELD, required = true) String price,
            @NotNull @JsonProperty(value = TYPE_FIELD, required = true) String type,
            @NotNull @JsonProperty(value = COLOR_NAME_FIELD, required = true) String colorName,
            @NotNull @JsonProperty(value = COLOR_RGB_FIELD, required = true) int colorRGB,
            @NotNull @JsonProperty(value = ISSUER_FIELD, required = true) String issuer,
            @NotNull @JsonProperty(value = MODEL_FIELD, required = true) String model) {
        this.id = idCounter.getAndIncrement();
        this.price = new BigDecimal(price);
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
    public long getId() {
        return id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getColorName() {
        return colorName;
    }

    public String getType() {
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