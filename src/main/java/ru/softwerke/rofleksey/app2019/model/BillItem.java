package ru.softwerke.rofleksey.app2019.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Objects;

public class BillItem {
    private static final String DEVICE_ID_FIELD = "device_id";
    private static final String QUANTITY_FIELD = "quantity";
    private static final String PRICE_FIELD = "price";

    @JsonProperty(DEVICE_ID_FIELD)
    private final long deviceId;

    @JsonProperty(QUANTITY_FIELD)
    private final int quantity;

    @JsonProperty(PRICE_FIELD)
    private final BigDecimal price;

    @JsonCreator
    public BillItem(
            @NotNull @JsonProperty(value = DEVICE_ID_FIELD, required = true) long deviceId,
            @NotNull @JsonProperty(value = QUANTITY_FIELD, required = true) int quantity,
            @NotNull @JsonProperty(value = PRICE_FIELD, required = true) BigDecimal price) {
        this.deviceId = deviceId;
        this.quantity = quantity;
        this.price = price;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BillItem that = (BillItem) o;
        return Objects.equals(deviceId, that.deviceId) &&
                Objects.equals(quantity, that.quantity) &&
                Objects.equals(price, that.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(deviceId, quantity, price);
    }

    public long getDeviceId() {
        return deviceId;
    }

    public int getQuantity() {
        return quantity;
    }

    @JsonIgnore
    public BigDecimal getTotalPrice() {
        return price.multiply(new BigDecimal(quantity));
    }

    public BigDecimal getPrice() {
        return price;
    }
}
