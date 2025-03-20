package com.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.validation.ConstraintViolationException;

/*
Global exception handler for the application.
Handles various exceptions and returns appropriate HTTP responses.

Includes handlers for:
- ResourceNotFoundException
- NoCommentExistException
- MethodArgumentNotValidException
- ConstraintViolationException
*/

@RestControllerAdvice
public class GlobalExceptionHandler {

	// Handles ResourceNotFoundException and returns a 404 status
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<String> handleResourceNotFoundException(ResourceNotFoundException ex) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
	}

	// Handles NoCommentExistException and returns a 404 status
	@ExceptionHandler(NoCommentExistException.class)
	public ResponseEntity<String> handleNoCommentExistException(NoCommentExistException ex) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
	}

	// Handles validation errors for method arguments and returns a 400 status
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getFieldErrors()
				.forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
	}

	// Handles constraint violations and returns a 400 status
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<Map<String, String>> handleConstraintViolationException(ConstraintViolationException ex) {
		Map<String, String> errors = new HashMap<>();

		ex.getConstraintViolations().forEach(violation -> {
			String propertyPath = violation.getPropertyPath().toString();
			String fieldName = propertyPath.substring(propertyPath.lastIndexOf(".") + 1); // Extract last part
			errors.put(fieldName, violation.getMessage());
		});

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
	}
}
