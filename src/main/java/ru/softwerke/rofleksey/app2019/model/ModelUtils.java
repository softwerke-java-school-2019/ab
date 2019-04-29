package ru.softwerke.rofleksey.app2019.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class ModelUtils {

    /**
     * Converts LocalDate to epoch (UTC)
     *
     * @param date date
     * @return epoch long
     */
    public static long localDateToLong(LocalDate date) {
        return date.atStartOfDay().toInstant(ZoneOffset.UTC).toEpochMilli();
    }

    public static long localDateTimeToLong(LocalDateTime dateTime) {
        return dateTime.toInstant(ZoneOffset.UTC).toEpochMilli();
    }
}
