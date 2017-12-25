package io.junq.examples.common.persistence.exception;

/**
 * 实体找不到异常
 * @author junqiangliu
 *
 */
public class IJEntityNotFoundException extends RuntimeException {
	
	public IJEntityNotFoundException() {
        super();
    }

    public IJEntityNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public IJEntityNotFoundException(final String message) {
        super(message);
    }

    public IJEntityNotFoundException(final Throwable cause) {
        super(cause);
    }
    
}
