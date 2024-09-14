package com.airfranceklm.fasttrack.assignment.config;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class WebRestControllerAdvice {

    /**
     *
     * @param ex Thrown Exception
     * @param response HttpServletResponse
     * @implNote To generate 400 Bad request if CustomException is thrown
     * @return A JSON with timestamp and message and a status code 400
     */
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<Map<String, Object>> handleAccessExceptions(CustomException ex, HttpServletResponse response) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", ex.getMessage());
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    public static class CustomException extends Exception {
        public CustomException(String message) {
            super(message);
        }
    }
}
