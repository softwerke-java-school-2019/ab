package ru.softwerke.rofleksey.app2019.controller.rest;

public class Error {
    private final InnerError error;

    private Error(InnerError e) {
        this.error = e;
    }

    static Error getInstance(String type, String msg) {
        return new Error(new InnerError(type, msg));
    }

    public InnerError getError() {
        return error;
    }

    public static class InnerError {
        private final String type;
        private final String message;

        private InnerError(String type, String msg) {
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
