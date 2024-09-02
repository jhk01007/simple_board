package org.example.recipe_board_jpa.exception;

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
