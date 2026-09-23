package com.cput.traabcobusinessplatform.engagement.dto;


import com.cput.traabcobusinessplatform.engagement.domain.enums.EngagementStatus;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Outbound DTO — what the API sends back after any engagement operation.
 * Returns IDs of related entities rather than full nested objects
 * to keep the response clean and avoid circular references.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EngagementResponse {
    private Long id;
    private Long clientId;
    private Long serviceId;
    private Long userId;
    private Long bookingId;
    private LocalDate startDate;
    private LocalDate endDate;
    private EngagementStatus status;
    private LocalDateTime createdAt;

}
