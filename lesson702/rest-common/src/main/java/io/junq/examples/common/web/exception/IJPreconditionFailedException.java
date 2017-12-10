package io.junq.examples.common.web.exception;

public final class IJPreconditionFailedException extends RuntimeException {

    public IJPreconditionFailedException() {
        super();
    }

    public IJPreconditionFailedException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public IJPreconditionFailedException(final String message) {
        super(message);
    }

    public IJPreconditionFailedException(final Throwable cause) {
        super(cause);
    }

	
}
