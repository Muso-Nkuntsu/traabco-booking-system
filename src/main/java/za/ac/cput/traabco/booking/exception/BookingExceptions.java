package za.ac.cput.traabco.booking.exception;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class BookingExceptions {



// ─── 404 ─────────────────────────────────────────────────────────────────────

    @ResponseStatus(HttpStatus.NOT_FOUND)
    public class ResourceNotFoundException extends RuntimeException {
        public ResourceNotFoundException(String message) {
            super(message);
        }
    }

// ─── 409 ─────────────────────────────────────────────────────────────────────

    @ResponseStatus(HttpStatus.CONFLICT)
    public static class SchedulingConflictException extends RuntimeException {
        public SchedulingConflictException(String message) {
            super(message);
        }
    }

// ─── 422 ─────────────────────────────────────────────────────────────────────

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public static class InvalidBookingStateException extends RuntimeException {
        public InvalidBookingStateException(String message) {
            super(message);
        }
    }

}
