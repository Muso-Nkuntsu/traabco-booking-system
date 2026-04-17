package za.ac.cput.traabco.booking.dto;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import za.ac.cput.traabco.booking.domain.enums.BookingStatus;

/**
 * Payload used by Admin / Consultant to update the status of an existing booking.
 *
 * Example JSON (confirm):
  * {
 *   "status": "CONFIRMED",
 *   "consultantId": 7,
 *   "internalNotes": "Client confirmed via phone."
  * }
 *
         * Example JSON (cancel):
            * {
 *   "status": "CANCELLED",
 *   "cancellationReason": "Client requested reschedule."
                * }
 */
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public class BookingStatusUpdateRequest {

    @NotNull(message = "Status is required")
    private BookingStatus status;

    /**
     * Optionally (re)assign a consultant when confirming
     */
    private Long consultantId;

    @Size(max = 2000)
    private String internalNotes;

    @Size(max = 500)
    private String cancellationReason;

}
