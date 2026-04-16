package za.ac.cput.traabco.booking.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Centralised error response envelope and handler.
 *
 * Error JSON example (validation):
 * {
 *   "timestamp": "2025-08-01T10:05:00",
 *   "status": 400,
 *   "error": "Validation Failed",
 *   "message": "One or more fields are invalid",
 *   "fieldErrors": {
 *     "scheduledAt": "Scheduled date/time must be in the future",
 *     "clientId": "Client ID is required"
 *   }
 * }
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    // ── ApiError record ───────────────────────────────────────────────────

    @Getter
    public static class ApiError {

        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        private final LocalDateTime timestamp = LocalDateTime.now();
        private final int    status;
        private final String error;
        private final String message;
        private Map<String, String> fieldErrors;

        public ApiError(HttpStatus httpStatus, String message) {
            this.status  = httpStatus.value();
            this.error   = httpStatus.getReasonPhrase();
            this.message = message;
        }

        public ApiError withFieldErrors(Map<String, String> fieldErrors) {
            this.fieldErrors = fieldErrors;
            return this;
        }
    }

    // ── Handlers ──────────────────────────────────────────────────────────

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, String> fieldErrors = new HashMap<>();
        for (FieldError fe : ex.getBindingResult().getFieldErrors()) {
            fieldErrors.put(fe.getField(), fe.getDefaultMessage());
        }
        ApiError error = new ApiError(HttpStatus.BAD_REQUEST, "One or more fields are invalid")
                .withFieldErrors(fieldErrors);
        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(BookingExceptions.ResourceNotFoundException.class)
    public ResponseEntity<ApiError> handleNotFound(BookingExceptions.ResourceNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ApiError(HttpStatus.NOT_FOUND, ex.getMessage()));
    }

    @ExceptionHandler(BookingExceptions.SchedulingConflictException.class)
    public ResponseEntity<ApiError> handleConflict(BookingExceptions.SchedulingConflictException ex) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ApiError(HttpStatus.CONFLICT, ex.getMessage()));
    }

    @ExceptionHandler(BookingExceptions.InvalidBookingStateException.class)
    public ResponseEntity<ApiError> handleInvalidState(BookingExceptions.InvalidBookingStateException ex) {
        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(new ApiError(HttpStatus.UNPROCESSABLE_ENTITY, ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGeneric(Exception ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiError(HttpStatus.INTERNAL_SERVER_ERROR,
                        "An unexpected error occurred. Please try again later."));
    }
}
