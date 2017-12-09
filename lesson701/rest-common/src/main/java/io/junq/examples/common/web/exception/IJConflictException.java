package io.junq.examples.common.web.exception;

public final class IJConflictException extends RuntimeException {

	public IJConflictException() {
        super();
    }

    public IJConflictException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public IJConflictException(final String message) {
        super(message);
    }

    public IJConflictException(final Throwable cause) {
        super(cause);
    }
	
}
