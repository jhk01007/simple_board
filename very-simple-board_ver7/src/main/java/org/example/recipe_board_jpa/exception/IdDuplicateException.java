package org.example.recipe_board_jpa.exception;

public class IdDuplicateException extends RuntimeException{
    public IdDuplicateException() {
    }

    public IdDuplicateException(String message) {
        super(message);
    }

    public IdDuplicateException(String message, Throwable cause) {
        super(message, cause);
    }

    public IdDuplicateException(Throwable cause) {
        super(cause);
    }

    public IdDuplicateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
