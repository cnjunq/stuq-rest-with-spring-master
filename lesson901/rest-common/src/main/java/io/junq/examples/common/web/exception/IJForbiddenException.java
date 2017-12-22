package io.junq.examples.common.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN)
public final class IJForbiddenException extends RuntimeException {
	
    public IJForbiddenException() {
        super();
    }

    public IJForbiddenException(final String message) {
        super(message);
    }

    public IJForbiddenException(final Throwable cause) {
        super(cause);
    }

}
