package com.cput.traabcobusinessplatform.engagement.dto;


import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

/** Muso Nkuntsu -231223722
 * Inbound DTO — what is sent when creating an engagement manually.
 * Status always starts as ACTIVE — never accepted from request.
 * Auto-creation from Booking passes these fields directly.
 */

@Getter
@Setter
@NoArgsConstructor
public class EngagementRequest {

    @NotNull(message = "Client ID is required")
    private Long clientId;

    @NotNull(message = "Service ID is required")
    private Long serviceId;

    @NotNull(message = "User ID is required")
    private Long userId;

    @NotNull(message = "Booking ID is required")
    private Long bookingId;

    private LocalDate startDate;

    private LocalDate endDate;
}
