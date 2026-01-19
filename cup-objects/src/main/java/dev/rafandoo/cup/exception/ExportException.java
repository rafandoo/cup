package dev.rafandoo.cup.exception;

/**
 * Exception thrown when an export operation fails.
 */
public class ExportException extends RuntimeException {

    public ExportException(String message, Throwable cause) {
        super(message, cause);
    }
}
