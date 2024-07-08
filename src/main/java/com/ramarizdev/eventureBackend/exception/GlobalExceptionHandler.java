package com.ramarizdev.eventureBackend.exception;

import com.ramarizdev.eventureBackend.response.Response;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.nio.file.AccessDeniedException;
import java.util.stream.Collectors;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Response<String>> handleIllegalArgumentException(IllegalArgumentException exception) {
        return Response.failed(HttpStatus.BAD_REQUEST.value(), exception.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Response<String>> handleValidationException(MethodArgumentNotValidException exception) {
        String errorMessage = exception.getBindingResult().getAllErrors().stream()
                .map(error -> ((FieldError) error).getField() + " " + error.getDefaultMessage()).collect(Collectors.joining(", "));
        return Response.failed(HttpStatus.BAD_REQUEST.value(), errorMessage);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Response<String>> handleBadCredentialsException(BadCredentialsException exception) {
        return Response.failed(HttpStatus.BAD_REQUEST.value(), "Invalid email or password");
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Response<Object>> handleDataIntegrityViolationException(DataIntegrityViolationException exception) {
        String message = extractErrorMessage(exception);

        return Response.failed(HttpStatus.CONFLICT.value(), message);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Response<Object>> handleEntityNotFoundException(EntityNotFoundException exception) {
        String message = exception.getMessage();

        return Response.failed(HttpStatus.NOT_FOUND.value(), message);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Response<String>> handleAccessDeniedException(AccessDeniedException ex) {
        return Response.failed(HttpStatus.FORBIDDEN.value(), ex.getMessage());
    }

//

    private String extractErrorMessage(DataIntegrityViolationException ex) {
        if (ex.getMessage().contains("unique constraint")) {
            return "Duplicate value violation: " + ex.getMostSpecificCause().getMessage();
        }
        return "Data integrity violation: " + ex.getMostSpecificCause().getMessage();
    }
}

