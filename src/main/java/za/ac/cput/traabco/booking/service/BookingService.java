package za.ac.cput.traabco.booking.service;




import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import za.ac.cput.traabco.booking.domain.enums.BookingStatus;
import za.ac.cput.traabco.booking.dto.BookingRequest;
import za.ac.cput.traabco.booking.dto.BookingResponse;
import za.ac.cput.traabco.booking.dto.BookingStatusUpdateRequest;

/**
 * Contract for all booking business operations.
 */
public interface BookingService {

    /**
     * Creates a new booking for the given client and service.
     *
     * @param request validated booking request DTO
     * @return persisted booking as response DTO
     */
    BookingResponse createBooking(BookingRequest request);

    /**
     * Fetches a single booking by its ID.
     *
     * @throws com.businessplatform.booking.exception.ResourceNotFoundException if not found
     */
    BookingResponse getBookingById(Long id);

    /**
     * Returns a paginated list of all bookings.
     */
    Page<BookingResponse> getAllBookings(Pageable pageable);

    /**
     * Returns a paginated list of bookings for a specific client.
     */
    Page<BookingResponse> getBookingsByClient(Long clientId, Pageable pageable);

    /**
     * Returns a paginated list of bookings assigned to a specific consultant.
     */
    Page<BookingResponse> getBookingsByConsultant(Long consultantId, Pageable pageable);

    /**
     * Returns a paginated list of bookings filtered by status.
     */
    Page<BookingResponse> getBookingsByStatus(BookingStatus status, Pageable pageable);

    /**
     * Updates the status (and optionally the consultant) of an existing booking.
     * Enforces valid state transitions.
     *
     * @param id      booking identifier
     * @param request status-update payload
     * @return updated booking as response DTO
     */
    BookingResponse updateBookingStatus(Long id, BookingStatusUpdateRequest request);

    /**
     * Allows a client to cancel their own booking (only if PENDING or CONFIRMED).
     *
     * @param id                 booking identifier
     * @param cancellationReason optional reason
     */
    BookingResponse cancelBooking(Long id, String cancellationReason);

    /**
     * Hard-deletes a booking. Restricted to ADMIN role.
     */
    void deleteBooking(Long id);
}

