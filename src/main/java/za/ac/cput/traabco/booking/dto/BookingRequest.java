package za.ac.cput.traabco.booking.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;


/**
 * Payload used when a client creates a new booking.
 *
 * Example JSON:
 * {
 *   "clientId":   1,
 *   "serviceId":  3,
 *   "consultantId": 7,
 *   "scheduledAt": "2025-09-15T10:00:00",
 *   "durationMinutes": 60,
 *   "clientNotes": "Please prepare the Q3 financial report beforehand."
 * }
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingRequest {
        @NotNull(message = "Client ID is required")
        @Positive(message = "Client ID must be a positive number")
        private Long clientId;

        @NotNull(message = "Service ID is required")
        @Positive(message = "Service ID must be a positive number")
        private Long serviceId;

        /** Optional — may be assigned later by an admin */
        private Long consultantId;

        @NotNull(message = "Scheduled date/time is required")
        @Future(message = "Scheduled date/time must be in the future")
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        private LocalDateTime scheduledAt;

        @Positive(message = "Duration must be a positive number of minutes")
        private Integer durationMinutes;

        /** Optional agreed price override; if null, service.basePrice is used */
        @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than zero")
        @Digits(integer = 8, fraction = 2, message = "Price format invalid")
        private BigDecimal agreedPrice;

        @Size(max = 2000, message = "Notes must not exceed 2000 characters")
        private String clientNotes;
}
