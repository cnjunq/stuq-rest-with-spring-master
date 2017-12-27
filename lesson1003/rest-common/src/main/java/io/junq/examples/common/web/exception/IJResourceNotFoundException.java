package io.junq.examples.common.web.exception;

public final class IJResourceNotFoundException extends RuntimeException {

    public IJResourceNotFoundException() {
        super();
    }

    public IJResourceNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public IJResourceNotFoundException(final String message) {
        super(message);
    }

    public IJResourceNotFoundException(final Throwable cause) {
        super(cause);
    }
	
}
