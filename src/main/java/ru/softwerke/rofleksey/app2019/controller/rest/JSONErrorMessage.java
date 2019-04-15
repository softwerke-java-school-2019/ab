package ru.softwerke.rofleksey.app2019.controller.rest;

/**
 * JSON error representation
 */
public class JSONErrorMessage {
    private final ErrorDescription error;

    private JSONErrorMessage(ErrorDescription e) {
        this.error = e;
    }

    /**
     * Constructs error representation
     *
     * @param type error type
     * @param msg  error message
     * @return error representation
     */
    static JSONErrorMessage create(String type, String msg) {
        return new JSONErrorMessage(new ErrorDescription(type, msg));
    }

    public ErrorDescription getError() {
        return error;
    }

    public static class ErrorDescription {
        private final String type;
        private final String message;

        private ErrorDescription(String type, String msg) {
            this.type = type;
            this.message = msg;
        }

        public String getType() {
            return type;
        }

        public String getMessage() {
            return message;
        }
    }
}
