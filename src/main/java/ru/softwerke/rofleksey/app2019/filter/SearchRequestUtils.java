package ru.softwerke.rofleksey.app2019.filter;

import java.util.function.Function;

public class SearchRequestUtils {
    static <T> T parseNumber(String numberString, Function<String, T> conversion) throws MalformedSearchRequestException {
        try {
            return conversion.apply(numberString);
        } catch (NumberFormatException e) {
            throw new MalformedSearchRequestException("number expected", numberString);
        }
    }
}
