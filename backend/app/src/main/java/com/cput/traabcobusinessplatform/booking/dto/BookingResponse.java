package com.cput.traabcobusinessplatform.booking.dto;


import com.cput.traabcobusinessplatform.booking.enums.BookingStatus;
import lombok.*;

import java.time.LocalDateTime;

/**Muso Nkuntsu-231223722
 * Outbound DTO - What the API sends back after any booking operation.
 * Returns IDs of related entities rather than full nested objects
 * to keep the response clean and avoid circular references
 * */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingResponse {
    private Long id;
    private Long clientId;
    private Long serviceId;
    private Long userId;
    private LocalDateTime bookingDate;
    private LocalDateTime scheduleAt;
    private BookingStatus status;
    private String notes;
}
