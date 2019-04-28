package ru.softwerke.rofleksey.app2019.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Objects;

public class Bill implements Model {
    public static final String DATE_FORMAT = "dd.MM.yyyy HH:mm:ss";

    private static final String ID_FIELD = "id";
    private static final String CLIENT_ID_FIELD = "clientId";
    private static final String ITEMS_LIST_FIELD = "items";
    private static final String DATE_FIELD = "date";

    @JsonProperty(ID_FIELD)
    private long id = -1;

    @JsonProperty(CLIENT_ID_FIELD)
    private final long clientId;

    @JsonProperty(ITEMS_LIST_FIELD)
    @Valid
    @NotNull(message = "'items' field is null")
    private final List<@Valid @NotNull(message = "'items' array contains null object") BillItem> items;

    @JsonProperty(DATE_FIELD)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT)
    @NotNull(message = "date is null")
    private final LocalDateTime date;

    @JsonIgnore
    private long dateLong;

    @JsonIgnore
    private long dateStartOfTheDayLong;

    @JsonIgnore
    private BigDecimal totalPrice;

    @JsonIgnore
    private double totalPriceDouble;

    @JsonCreator
    public Bill(
            @JsonProperty(value = CLIENT_ID_FIELD, required = true) long clientId,
            @JsonProperty(value = ITEMS_LIST_FIELD, required = true) List<BillItem> items,
            @JsonProperty(value = DATE_FIELD, required = true)
            @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT) LocalDateTime date) {
        this.clientId = clientId;
        this.items = items;
        this.date = date;
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
        totalPrice = items.stream().map(BillItem::getTotalPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
        totalPriceDouble = totalPrice.doubleValue();
        dateLong = date.toInstant(ZoneOffset.UTC).toEpochMilli();
        dateStartOfTheDayLong = LocalDateTime.of(date.toLocalDate(), LocalTime.MIDNIGHT).toInstant(ZoneOffset.UTC).toEpochMilli();
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bill bill = (Bill) o;
        return id == bill.id &&
                clientId == bill.clientId &&
                dateLong == bill.dateLong &&
                Double.compare(bill.totalPriceDouble, totalPriceDouble) == 0 &&
                items.equals(bill.items);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, clientId, items, dateLong, totalPriceDouble);
    }

    @Override
    public String toString() {
        return "Bill{" +
                "id=" + id +
                ", clientId=" + clientId +
                ", items=" + items +
                ", date=" + date +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
