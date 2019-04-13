package ru.softwerke.rofleksey.app2019.filter;

public class MalformedSearchRequestException extends Exception {
    MalformedSearchRequestException(String message) {
        super(message);
    }

    MalformedSearchRequestException(String description, String cause) {
        super(description + ": '" + cause + "'");
    }
}
