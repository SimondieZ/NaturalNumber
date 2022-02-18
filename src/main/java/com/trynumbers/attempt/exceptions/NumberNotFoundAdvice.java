package com.trynumbers.attempt.exceptions;

import java.time.LocalDateTime;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;


/**
 * An interceptor of exceptions thrown by methods in {@link com.trynumbers.attempt.controller.NumbersController NumbersController}
 *  annotated with @GetMapping and similar. 
 * 
 * @author Serafim Sokhin
 */
@ControllerAdvice
public class NumberNotFoundAdvice {
	
	/**
	 * The exception handler method takes in an NumberNotFoundException or EmptyResultDataAccessException as an argument that handles in this method. 
	 * @param ex - NumberNotFoundException or EmptyResultDataAccessException
	 * @param request - WebRequest
	 * @return response entity with error details and Http status code 404.
	 */
	@ExceptionHandler({NumberNotFoundException.class, EmptyResultDataAccessException.class})
	public ResponseEntity<?> numberNotFoundHandler(Exception ex, WebRequest request) {
		ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), ex.getMessage(), request.getDescription(false));
		return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
	}
	
	
	/**
	 * The exception handler method takes in any Exception as an argument that handles in this method. 
	 * @param ex - Exception
	 * @param request - WebRequest
	 * @return response entity with error details and Http status code 500.
	 */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> globleExcpetionHandler(Exception ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
