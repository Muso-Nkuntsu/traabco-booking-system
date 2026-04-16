package za.ac.cput.traabco.booking.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import za.ac.cput.traabco.booking.domain.enums.BookingStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Returned for every booking read/write operation.
 *
 * Example JSON:
 * {
 *   "id": 42,
 *   "status": "CONFIRMED",
 *   "clientId": 1,
 *   "clientName": "Jane Smith",
 *   "serviceId": 3,
 *   "serviceName": "Financial Audit Consultation",
 *   "consultantId": 7,
 *   "consultantName": "John Doe",
 *   "scheduledAt": "2025-09-15T10:00:00",
 *   "durationMinutes": 60,
 *   "agreedPrice": 350.00,
 *   "clientNotes": "Please prepare the Q3 financial report beforehand.",
 *   "internalNotes": null,
 *   "cancellationReason": null,
 *   "createdAt": "2025-08-01T09:23:11",
 *   "updatedAt": "2025-08-01T09:23:11"
 * }
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingResponse {

    private Long id;
    private BookingStatus status;

    // Client
    private Long   clientId;
    private String clientName;

    // Service
    private Long   serviceId;
    private String serviceName;

    // Consultant (nullable)
    private Long   consultantId;
    private String consultantName;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime scheduledAt;

    private Integer    durationMinutes;
    private BigDecimal agreedPrice;
    private String     clientNotes;
    private String     internalNotes;
    private String     cancellationReason;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime updatedAt;
}
