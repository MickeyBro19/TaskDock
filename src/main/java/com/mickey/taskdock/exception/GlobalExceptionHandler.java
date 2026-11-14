package com.mickey.taskdock.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(TaskNotFoundException.class)
	public ResponseEntity<Map<String, Object>> handleNotFound(
			TaskNotFoundException exception
	                                                         ) {
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(Map.of(
						"timestamp", LocalDateTime.now(),
						"status", 404,
						"error", "Not Found",
						"message", exception.getMessage()
				            ));
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, Object>> handleValidation(
			MethodArgumentNotValidException exception
	                                                           ) {
		
		Map<String, String> errors = exception.getBindingResult()
				.getFieldErrors()
				.stream()
				.collect(Collectors.toMap(
						FieldError :: getField,
						error -> error.getDefaultMessage() == null
								? "Invalid value"
								: error.getDefaultMessage(),
						(first, second) -> first
				                         ));
		
		return ResponseEntity.badRequest()
				.body(Map.of(
						"timestamp", LocalDateTime.now(),
						"status", 400,
						"error", "Bad Request",
						"messages", errors
				            ));
	}
	
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<Map<String, Object>> handleBadRequest(
			IllegalArgumentException exception
	                                                           ) {
		
		return ResponseEntity.badRequest()
				.body(Map.of(
						"timestamp", LocalDateTime.now(),
						"status", 400,
						"error", "Bad Request",
						"message", exception.getMessage()
				            ));
	}
}
