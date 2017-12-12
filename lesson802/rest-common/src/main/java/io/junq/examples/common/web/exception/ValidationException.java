package io.junq.examples.common.web.exception;

public class ValidationException extends RuntimeException {

    public ValidationException(final String message) {
        super(message);
    }

}