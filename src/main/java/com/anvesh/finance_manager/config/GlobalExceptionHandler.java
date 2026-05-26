package com.anvesh.finance_manager.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // HANDLE CUSTOM / RUNTIME ERRORS
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, Object>>
    handleRuntimeException(RuntimeException ex) {

        Map<String, Object> error =
                new HashMap<>();

        error.put(
                "timestamp",
                LocalDateTime.now()
        );

        error.put(
                "status",
                HttpStatus.BAD_REQUEST.value()
        );

        error.put(
                "error",
                "Bad Request"
        );

        error.put(
                "message",
                ex.getMessage() != null
                        ? ex.getMessage()
                        : "Something went wrong"
        );

        return new ResponseEntity<>(
                error,
                HttpStatus.BAD_REQUEST
        );
    }

    // HANDLE ALL OTHER ERRORS
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>>
    handleException(Exception ex) {

        Map<String, Object> error =
                new HashMap<>();

        error.put(
                "timestamp",
                LocalDateTime.now()
        );

        error.put(
                "status",
                HttpStatus.INTERNAL_SERVER_ERROR.value()
        );

        error.put(
                "error",
                "Internal Server Error"
        );

        error.put(
                "message",
                "Server error occurred"
        );

        // PRINT ACTUAL ERROR IN CONSOLE
        ex.printStackTrace();

        return new ResponseEntity<>(
                error,
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}