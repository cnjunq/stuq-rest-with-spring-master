package io.junq.examples.common.web;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import io.junq.examples.common.persistence.exception.IJEntityNotFoundException;
import io.junq.examples.common.web.exception.ApiError;
import io.junq.examples.common.web.exception.IJBadRequestException;
import io.junq.examples.common.web.exception.IJConflictException;
import io.junq.examples.common.web.exception.IJResourceNotFoundException;
import io.junq.examples.common.web.exception.ValidationErrorDTO;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    public RestResponseEntityExceptionHandler() {
        super();
    }

    // API

    // 400
   
    @Override
	protected final ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		return handleExceptionInternal(ex, message(HttpStatus.BAD_REQUEST, ex), headers,
				HttpStatus.BAD_REQUEST, request);
	}
   
    @Override
	protected final ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

    	final BindingResult result = ex.getBindingResult();
    	final List<FieldError> fieldErrors = result.getFieldErrors();
    	final ValidationErrorDTO dto = processFieldErrors(fieldErrors);

        return handleExceptionInternal(ex, dto, headers, HttpStatus.BAD_REQUEST, request);

	}
    
    // 400
    
    @ExceptionHandler(value = {DataIntegrityViolationException.class, IJBadRequestException.class})
    public ResponseEntity<Object> handleBadRequest(final RuntimeException ex, final WebRequest request) {
    	
    	if (ex instanceof DataIntegrityViolationException) {
    		if (ExceptionUtils.getRootCauseMessage(ex).contains("uplicate") || ExceptionUtils.getRootCauseMessage(ex).contains("Unique")) {
                final ApiError apiError = message(HttpStatus.CONFLICT, ex);
                return handleExceptionInternal(ex, apiError, new HttpHeaders(), HttpStatus.CONFLICT, request);
            }
    	}
    	
    	return handleExceptionInternal(ex, message(HttpStatus.BAD_REQUEST, ex), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }
    
    // 403

    @ExceptionHandler({ AccessDeniedException.class })
    protected ResponseEntity<Object> handleEverything(final AccessDeniedException ex, final WebRequest request) {
        final ApiError apiError = message(HttpStatus.FORBIDDEN, ex);

        return handleExceptionInternal(ex, apiError, new HttpHeaders(), HttpStatus.FORBIDDEN, request);
    }
    
    // 404
    @ExceptionHandler({ EntityNotFoundException.class, IJEntityNotFoundException.class, IJResourceNotFoundException.class })
    protected ResponseEntity<Object> handleNotFound(final RuntimeException ex, final WebRequest request) {
        final ApiError apiError = message(HttpStatus.NOT_FOUND, ex);
        return handleExceptionInternal(ex, apiError, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }
    
    // 409
    @ExceptionHandler({ InvalidDataAccessApiUsageException.class, DataAccessException.class, IJConflictException.class })
    protected ResponseEntity<Object> handleConflict(final RuntimeException ex, final WebRequest request) {
        final ApiError apiError = message(HttpStatus.CONFLICT, ex);
        return handleExceptionInternal(ex, apiError, new HttpHeaders(), HttpStatus.CONFLICT, request);
    }
    
    // 500
    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<Object> handleServerError(final RuntimeException ex, final WebRequest request) {
    	return handleExceptionInternal(ex, message(HttpStatus.INTERNAL_SERVER_ERROR, ex), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }
    
    private final ApiError message(final HttpStatus httpStatus, final Exception ex) {
    	final String message = ex.getMessage() == null ? ex.getClass().getSimpleName() : ex.getMessage();
    	final String developerMessage = ExceptionUtils.getRootCauseMessage(ex);
    	
    	return new ApiError(httpStatus.value(), message, developerMessage);
    }
    
    // 工具方法
    
    private ValidationErrorDTO processFieldErrors(final List<FieldError> fieldErrors) {
        final ValidationErrorDTO dto = new ValidationErrorDTO();

        for (final FieldError fieldError : fieldErrors) {
            final String localizedErrorMessage = fieldError.getDefaultMessage();
            dto.addFieldError(fieldError.getField(), localizedErrorMessage);
        }

        return dto;
    }
    
}
