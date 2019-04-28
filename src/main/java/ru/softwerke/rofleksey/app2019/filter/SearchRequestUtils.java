package ru.softwerke.rofleksey.app2019.filter;

import java.time.format.DateTimeParseException;
import java.util.Map;
import java.util.function.BooleanSupplier;
import java.util.function.Function;

/**
 * Search Request utility class
 */
class SearchRequestUtils {
    /**
     * Attempts to parse string using specified function, throws MalformedSearchRequestException on fail
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
        } catch (IllegalArgumentException e) {
            throw new MalformedSearchRequestException("invalid enum type", numberString);
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

    /**
     * Creates FilterFactory instances for filtering by range using provided comparisonProducerFactory
     * e.g. price, priceFrom, priceTo
     * <p>
     * comparisonProducerFactory must return function, that compares objects in the following way: otherObject (>,=,<) targetObject
     *
     * @param filterFactories           map to add generated factories
     * @param criteriaName              criteria name
     * @param comparisonProducerFactory function, that accepts string and returns comparison producer (T -> Integer)
     * @param <T>                       entity type
     */
    static <T> void addRange(Map<String, SearchRequest.FilterFactory<T>> filterFactories, String criteriaName,
                             SearchRequest.ComparisonProducerFactory<T> comparisonProducerFactory) {
        filterFactories.put(criteriaName, s -> {
            Function<T, Integer> comparisonProducer = comparisonProducerFactory.apply(s);
            return it -> comparisonProducer.apply(it) == 0;
        });
        filterFactories.put(criteriaName + "From", s -> {
            Function<T, Integer> comparisonProducer = comparisonProducerFactory.apply(s);
            return it -> comparisonProducer.apply(it) >= 0;
        });
        filterFactories.put(criteriaName + "To", s -> {
            Function<T, Integer> comparisonProducer = comparisonProducerFactory.apply(s);
            return it -> comparisonProducer.apply(it) <= 0;
        });
    }
}
