package ru.softwerke.rofleksey.app2019.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

public class Bill implements Model {
    private static final String ID_FIELD = "id";
    private static final String CLIENT_ID_FIELD = "client_id";
    private static final String ITEMS_LIST_FIELD = "items_list";
    private static final String DATE_FIELD = "date";
    private static final String TIME_FIELD = "time";

    private static AtomicLong idCounter = new AtomicLong(0);

    @JsonProperty(ID_FIELD)
    private final long id;

    @JsonProperty(CLIENT_ID_FIELD)
    private final long clientId;

    @JsonProperty(ITEMS_LIST_FIELD)
    private final List<BillItem> items;

    @JsonProperty(DATE_FIELD)
    private final long date;

    @JsonProperty(TIME_FIELD)
    private final long time;

    @JsonIgnore
    private BigDecimal totalPrice;

    @JsonIgnore
    private double totalPriceDouble;

    @JsonCreator
    public Bill(
            @NotNull @JsonProperty(value = CLIENT_ID_FIELD, required = true) long clientId,
            @NotNull @JsonProperty(value = ITEMS_LIST_FIELD, required = true) List<BillItem> items,
            @NotNull @JsonProperty(value = DATE_FIELD, required = true) long date,
            @NotNull @JsonProperty(value = TIME_FIELD, required = true) long time) {
        this.id = idCounter.getAndIncrement();
        this.clientId = clientId;
        this.items = items;
        this.totalPrice = this.items.stream().map(BillItem::getTotalPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
        this.date = date;
        this.time = time;
        this.totalPriceDouble = this.totalPrice.doubleValue();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bill that = (Bill) o;
        return id == that.id &&
                Objects.equals(clientId, that.clientId) &&
                Objects.equals(items, that.items) &&
                Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, clientId, items, date);
    }

    @Override
    public long getId() {
        return id;
    }

    public boolean containsDevice(long id) {
        return items.stream().anyMatch(it -> it.getDeviceId() == id);
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public long getClientId() {
        return clientId;
    }

    public List<BillItem> getItems() {
        return items;
    }

    public long getDate() {
        return date;
    }

    public long getTime() {
        return time;
    }

    public double getTotalPriceDouble() {
        return totalPriceDouble;
    }
}
