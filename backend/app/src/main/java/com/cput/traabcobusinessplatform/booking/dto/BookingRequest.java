package com.cput.traabcobusinessplatform.booking.dto;

import com.cput.traabcobusinessplatform.booking.enums.BookingStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**Muso Nkuntsu -231223722
 * Inbound DTO - what a client sends when requesting a service.
 * The status is not accepted here - it always starts as PENDING
 * Only the IDs of related entities are sent, never the full objects.
 * */

@Getter
@Setter
@NoArgsConstructor


public class BookingRequest {
    @NotNull(message = "Client ID is required")
    private Long clientId;

    @NotNull(message = "Service ID is required")
    private Long serviceId;

    @NotNull(message = "User ID is required")
    private Long userId;

    private LocalDateTime scheduleAt;

    private String notes;

    private BookingStatus status;
}
