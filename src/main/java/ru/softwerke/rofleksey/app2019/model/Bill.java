package ru.softwerke.rofleksey.app2019.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Objects;

public class Bill implements Model {
    private static final String ID_FIELD = "id";
    private static final String CLIENT_ID_FIELD = "clientId";
    private static final String ITEMS_LIST_FIELD = "items";
    private static final String DATE_FIELD = "date";

    public static final String DATE_FORMAT = "dd-MM-yyyy HH:mm:ss";

    @JsonProperty(ID_FIELD)
    private long id = -1;

    @JsonProperty(CLIENT_ID_FIELD)
    private final long clientId;

    @JsonProperty(ITEMS_LIST_FIELD)
    private final List<BillItem> items;

    @JsonProperty(DATE_FIELD)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT)
    private final LocalDateTime date;

    @JsonIgnore
    private final long dateLong;

    @JsonIgnore
    private final long dateStartOfTheDayLong;

    @JsonIgnore
    private BigDecimal totalPrice;

    @JsonIgnore
    private double totalPriceDouble;

    @JsonCreator
    public Bill(
            @NotNull @JsonProperty(value = CLIENT_ID_FIELD, required = true) long clientId,
            @NotNull @JsonProperty(value = ITEMS_LIST_FIELD, required = true) List<BillItem> items,
            @NotNull @JsonProperty(value = DATE_FIELD, required = true)
            @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT) LocalDateTime date) {
        this.clientId = clientId;
        this.items = items;
        this.totalPrice = this.items.stream().map(BillItem::getTotalPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
        this.date = date;
        this.dateLong = date.toInstant(ZoneOffset.UTC).toEpochMilli();
        this.dateStartOfTheDayLong = LocalDateTime.of(date.toLocalDate(), LocalTime.MIDNIGHT).toInstant(ZoneOffset.UTC).toEpochMilli();
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
    public String toString() {
        return "Bill{" +
                "id=" + id +
                ", clientId=" + clientId +
                ", items=" + items +
                ", date=" + date +
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

    public LocalDateTime getDate() {
        return date;
    }

    public long getDateLong() {
        return dateLong;
    }

    public long getDateStartOfTheDayLong() {
        return dateStartOfTheDayLong;
    }

    public double getTotalPriceDouble() {
        return totalPriceDouble;
    }
}
