package org.example.board.exception;

public class AuthenticatedException extends RuntimeException{
    public AuthenticatedException() {
    }

    public AuthenticatedException(String message) {
        super(message);
    }

    public AuthenticatedException(String message, Throwable cause) {
        super(message, cause);
    }

    public AuthenticatedException(Throwable cause) {
        super(cause);
    }

    public AuthenticatedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
