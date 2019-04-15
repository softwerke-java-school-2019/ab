package ru.softwerke.rofleksey.app2019.filter;

import java.time.format.DateTimeParseException;
import java.util.function.BooleanSupplier;
import java.util.function.Function;

/**
 * Search Request utility class
 */
class SearchRequestUtils {
    /**
     * Attempts to parse string using specified function, returns MalformeredSearchRequestException on fail
     * <p>
     * Parsing is considered a failure if NumberFormatException or DateTimeParseException is thrown
     *
     * @param numberString string to parse
     * @param parser       parse function
     * @param <T>          type of parse result
     * @return parse result
     * @throws MalformedSearchRequestException if parsing has failed
     */
    static <T> T parseString(String numberString, Function<String, T> parser) throws MalformedSearchRequestException {
        try {
            return parser.apply(numberString);
        } catch (NumberFormatException e) {
            throw new MalformedSearchRequestException("number expected", numberString);
        } catch (DateTimeParseException e) {
            throw new MalformedSearchRequestException("invalid date/time format", numberString);
        }
    }

    /**
     * Checks specified condition, throws MalformedSearchRequestException if it is false
     *
     * @param supplier condition
     * @param message error message
     * @throws MalformedSearchRequestException if condition is false
     */
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
