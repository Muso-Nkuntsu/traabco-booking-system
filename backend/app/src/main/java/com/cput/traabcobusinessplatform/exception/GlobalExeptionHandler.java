package com.cput.traabcobusinessplatform.exception;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.util.*;


@RestControllerAdvice
public class GlobalExeptionHandler {

    @Getter
    public static class ApiError {
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        private final LocalDateTime timeStamp = LocalDateTime.now();
        private final int status;
        private final String error;
        private final String message;
        private final String path;

        private Map<String, String> fiedlErrors;

        public ApiError(HttpStatus httpStatus, String message, String path) {
            this.status = httpStatus.value();
            this.error = httpStatus.getReasonPhrase();
            this.message = message;
            this.path = path;
        }
        ApiError withFieldErrors(Map<String, String> fieldErrors){
            this.fiedlErrors = fiedlErrors;
            return this;
        }
    }

    /// 400 validation

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidation(MethodArgumentNotValidException ex, HttpServletRequest req){
        Map<String, String > fieldErrors = new LinkedHashMap<>();
        for (FieldError fe : ex.getBindingResult().getFieldErrors()){
            fieldErrors.putIfAbsent(fe.getField(), fe.getDefaultMessage());
        }
        return ResponseEntity.badRequest().body(
                new ApiError(HttpStatus.BAD_REQUEST, "one or more fields are invalid", req.getRequestURI())
                        .withFieldErrors(fieldErrors));
    }
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiError> handleTypeMismatch(
            MethodArgumentTypeMismatchException ex, HttpServletRequest req){
        String msg = String.format("Parameter '%s' recieved invalid value '%s'", ex.getName(), ex.getValue());
        return ResponseEntity.badRequest().body(new ApiError(HttpStatus.BAD_REQUEST, msg,req.getRequestURI()));
    }

    ////404 Not Found
    @ExceptionHandler({
            UserNotFoundException.class,
            ClientNotFoundException.class,
            ServiceNotFoundException.class,
            BookingNotFoundException.class,
            PaymentNotFoundExcpetion.class,
            EngagementNotFoundException.class
    })
    public ResponseEntity<ApiError> handleNotFound(RuntimeException ex, HttpServletRequest req) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiError(HttpStatus.NOT_FOUND, ex.getMessage(), req.getRequestURI()));
    }

    // ── 409 Conflict ──────────────────────────────────────────────────────

    @ExceptionHandler(ClientEmailAlreadyExistsException.class)
    public ResponseEntity<ApiError> handleConflict(ClientEmailAlreadyExistsException ex, HttpServletRequest req) {
       return ResponseEntity.status(HttpStatus.CONFLICT)
               .body(new ApiError(HttpStatus.CONFLICT,
                       ex.getMessage(),
                       req.getRequestURI()));
    }

    // ── @ResponseStatus annotated exceptions (422, etc.) ─────────────────

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiError> handleAnnotatedRuntimeException(
            RuntimeException ex, HttpServletRequest req) {
        org.springframework.web.bind.annotation.ResponseStatus rs =
                ex.getClass().getAnnotation(
                        org.springframework.web.bind.annotation.ResponseStatus.class);
        if (rs != null) {
            HttpStatus status = rs.value();
            return ResponseEntity.status(status)
                    .body(new ApiError(status, ex.getMessage(), req.getRequestURI()));
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiError(HttpStatus.INTERNAL_SERVER_ERROR,
                        "An unexpected error occurred.", req.getRequestURI()));
    }

    // ── 500 Catch-all ─────────────────────────────────────────────────────

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGeneric(Exception ex,
                                                  HttpServletRequest req) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiError(HttpStatus.INTERNAL_SERVER_ERROR,
                        "An unexpected error occurred. Please try again later.", req.getRequestURI()));
    }
}

