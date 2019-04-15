package ru.softwerke.rofleksey.app2019.filter;

import java.time.format.DateTimeParseException;
import java.util.function.BooleanSupplier;
import java.util.function.Function;

class SearchRequestUtils {
    static <T> T parseString(String numberString, Function<String, T> parser) throws MalformedSearchRequestException {
        try {
            return parser.apply(numberString);
        } catch (NumberFormatException e) {
            throw new MalformedSearchRequestException("number expected", numberString);
        } catch (DateTimeParseException e) {
            throw new MalformedSearchRequestException("invalid date/time format", numberString);
        }
    }

    static void assertBool(BooleanSupplier supplier, String message) throws MalformedSearchRequestException {
        if (!supplier.getAsBoolean()) {
            throw new MalformedSearchRequestException(message);
        }
    }

    static void assertBool(BooleanSupplier supplier, String message, String cause) throws MalformedSearchRequestException {
        if (!supplier.getAsBoolean()) {
            throw new MalformedSearchRequestException(message, cause);
        }
    }
}
