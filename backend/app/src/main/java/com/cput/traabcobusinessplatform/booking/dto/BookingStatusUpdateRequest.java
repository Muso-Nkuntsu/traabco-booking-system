package com.cput.traabcobusinessplatform.booking.dto;


import com.cput.traabcobusinessplatform.booking.enums.BookingStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**Muso Nkuntsu -231223722
 *  * Inbound DTO used specifically for updating a booking's status.
 *  * Kept separate from BookingRequest so status changes are
 *  * a deliberate, explicit action rather than part of a general update.*/

@Getter
@Setter
@NoArgsConstructor
public class BookingStatusUpdateRequest {
    @NotNull(message = "Booking status is required")
    private BookingStatus status;
}
