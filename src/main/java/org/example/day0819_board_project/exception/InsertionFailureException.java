package org.example.day0819_board_project.exception;

public class InsertionFailureException extends RuntimeException{

    public InsertionFailureException() {
    }

    public InsertionFailureException(String message) {
        super(message);
    }

    public InsertionFailureException(String message, Throwable cause) {
        super(message, cause);
    }

    public InsertionFailureException(Throwable cause) {
        super(cause);
    }

    public InsertionFailureException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
