package ru.softwerke.rofleksey.app2019.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Bill implements Model {
    public static final String DATE_FORMAT = "dd.MM.yyyy HH:mm:ss";

    private static final String ID_FIELD = "id";
    private static final String CUSTOMER_ID = "customerId";
    private static final String ITEMS_LIST_FIELD = "items";
    private static final String PURCHASE_DATE_TIME_FIELD = "purchaseDateTime";
    private static final String TOTAL_PRICE_FIELD = "totalPrice";

    @JsonProperty(ITEMS_LIST_FIELD)
    @Valid
    @NotEmpty(message = "'items' field is empty or null")
    private final List<@Valid @NotNull(message = "'items' array contains null object") BillItem> items;

    @JsonProperty(CUSTOMER_ID)
    private final long customerId;
    @JsonIgnore
    @NotNull(message = "purchaseDateTime is null")
    private final LocalDateTime purchaseDateTime;
    @JsonIgnore
    private long id;

    @JsonIgnore
    private long dateLong;

    @JsonIgnore
    private BigDecimal totalPrice;

    @JsonIgnore
    private double totalPriceDouble;

    @JsonCreator
    public Bill(
            @JsonProperty(value = CUSTOMER_ID, required = true) long customerId,
            @JsonProperty(value = ITEMS_LIST_FIELD, required = true) List<BillItem> items) {
        this.customerId = customerId;
        this.items = items == null ? null : Collections.unmodifiableList(items);
        this.purchaseDateTime = LocalDateTime.now(ZoneOffset.UTC);
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
    public void init() {
        totalPrice = items.stream().map(BillItem::getTotalPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
        totalPriceDouble = totalPrice.doubleValue();
        dateLong = ModelUtils.localDateTimeToLong(purchaseDateTime);
    }

    public boolean containsDevice(long id) {
        return items.stream().anyMatch(it -> it.getDeviceId() == id);
    }

    @JsonProperty(TOTAL_PRICE_FIELD)
    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public long getCustomerId() {
        return customerId;
    }

    public List<BillItem> getItems() {
        return items;
    }

    @JsonProperty(PURCHASE_DATE_TIME_FIELD)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT)
    public LocalDateTime getPurchaseDateTime() {
        return purchaseDateTime;
    }

    public long getDateLong() {
        return dateLong;
    }

    public double getTotalPriceDouble() {
        return totalPriceDouble;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bill bill = (Bill) o;
        return id == bill.id &&
                customerId == bill.customerId &&
                dateLong == bill.dateLong &&
                Double.compare(bill.totalPriceDouble, totalPriceDouble) == 0 &&
                items.equals(bill.items);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, customerId, items, dateLong, totalPriceDouble);
    }

    @Override
    public String toString() {
        return "Bill{" +
                "id=" + id +
                ", customerId=" + customerId +
                ", items=" + items +
                ", purchaseDateTime=" + purchaseDateTime +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
