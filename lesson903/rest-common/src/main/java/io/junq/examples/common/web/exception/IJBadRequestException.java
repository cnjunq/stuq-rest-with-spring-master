package io.junq.examples.common.web.exception;

public class IJBadRequestException extends RuntimeException {

	public IJBadRequestException() {
        super();
    }

    public IJBadRequestException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public IJBadRequestException(final String message) {
        super(message);
    }

    public IJBadRequestException(final Throwable cause) {
        super(cause);
    }
	
}
