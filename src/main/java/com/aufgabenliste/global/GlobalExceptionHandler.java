package com.aufgabenliste.global;

import com.aufgabenliste.task.exceptions.TaskNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

	private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	// Handle validation errors
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiError> handleValidationExceptions(
			MethodArgumentNotValidException ex,
			HttpServletRequest request) {

		String validationErrors = ex.getBindingResult()
				.getFieldErrors()
				.stream()
				.map(error -> error.getField() + ": " + error.getDefaultMessage())
				.collect(Collectors.joining(", "));

		log.warn("Validation failed at [{}]: {}", request.getRequestURI(), validationErrors, ex);

		ApiError apiError = new ApiError(
				HttpStatus.BAD_REQUEST.value(),
				HttpStatus.BAD_REQUEST.getReasonPhrase(),
				validationErrors,
				request.getRequestURI());

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
	}

	// Handle task not found
	@ExceptionHandler(TaskNotFoundException.class)
	public ResponseEntity<ApiError> handleTaskNotFound(TaskNotFoundException ex, HttpServletRequest request) {
		log.info("Task not found at [{}]: {}", request.getRequestURI(), ex.getMessage());

		ApiError apiError = new ApiError(
				HttpStatus.NOT_FOUND.value(),
				HttpStatus.NOT_FOUND.getReasonPhrase(),
				ex.getMessage(),
				request.getRequestURI());

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);
	}

	// Catch-all for unexpected errors
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiError> handleGeneralException(Exception ex, HttpServletRequest request) {
		log.error("Unexpected error at [{}]: {}", request.getRequestURI(), ex.getMessage(), ex);

		ApiError apiError = new ApiError(
				HttpStatus.INTERNAL_SERVER_ERROR.value(),
				HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
				"An unexpected error occurred",
				request.getRequestURI());

		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiError);
	}
}
