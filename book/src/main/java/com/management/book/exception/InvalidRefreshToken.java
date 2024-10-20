package com.management.book.exception;

public class InvalidRefreshToken extends Exception {
    public InvalidRefreshToken() {
        super();
    }

    public InvalidRefreshToken(String message) {
        super(message);
    }

    public InvalidRefreshToken(String message, Throwable cause) {
        super(message, cause);
    }
}
