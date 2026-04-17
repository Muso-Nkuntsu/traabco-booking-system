package za.ac.cput.traabco.booking.controllers;

import za.ac.cput.traabco.booking.domain.enums.BookingStatus;
import za.ac.cput.traabco.booking.dto.BookingRequest;
import za.ac.cput.traabco.booking.dto.BookingResponse;
import za.ac.cput.traabco.booking.dto.BookingStatusUpdateRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
 * REST controller for Booking operations.
 *
 * Base path: /api/v1/bookings
 *
 * ┌─────────────────────────────────────────────────────────────────────┐
 * │  Method  │ Path                              │ Description           │
 * ├─────────────────────────────────────────────────────────────────────┤
 * │  POST    │ /                                 │ Create booking        │
 * │  GET     │ /                                 │ List all (paginated)  │
 * │  GET     │ /{id}                             │ Get by ID             │
 * │  GET     │ /client/{clientId}                │ By client             │
 * │  GET     │ /consultant/{consultantId}         │ By consultant         │
 * │  GET     │ /status/{status}                  │ By status             │
 * │  PATCH   │ /{id}/status                      │ Update status         │
 * │  PATCH   │ /{id}/cancel                      │ Client cancel         │
 * │  DELETE  │ /{id}                             │ Hard delete (ADMIN)   │
 * └─────────────────────────────────────────────────────────────────────┘
 */
@RestController
@RequestMapping("/api/v1/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    // ── CREATE ────────────────────────────────────────────────────────────

    /**
     * POST /api/v1/bookings
     *
     * Request body:
     * {
     *   "clientId": 1,
     *   "serviceId": 3,
     *   "consultantId": 7,
     *   "scheduledAt": "2025-09-15T10:00:00",
     *   "durationMinutes": 60,
     *   "clientNotes": "Please bring the Q3 report."
     * }
     *
     * Response: 201 Created + BookingResponse
     */
    @PostMapping
    public ResponseEntity<BookingResponse> createBooking(
            @Valid @RequestBody BookingRequest request) {
        BookingResponse response = bookingService.createBooking(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // ── READ ──────────────────────────────────────────────────────────────

    /**
     * GET /api/v1/bookings?page=0&size=20&sort=scheduledAt,asc
     */
    @GetMapping
    public ResponseEntity<Page<BookingResponse>> getAllBookings(
            @RequestParam(defaultValue = "0")           int page,
            @RequestParam(defaultValue = "20")          int size,
            @RequestParam(defaultValue = "scheduledAt") String sortBy,
            @RequestParam(defaultValue = "asc")         String direction) {

        Pageable pageable = buildPageable(page, size, sortBy, direction);
        return ResponseEntity.ok(bookingService.getAllBookings(pageable));
    }

    /**
     * GET /api/v1/bookings/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<BookingResponse> getBookingById(@PathVariable Long id) {
        return ResponseEntity.ok(bookingService.getBookingById(id));
    }

    /**
     * GET /api/v1/bookings/client/{clientId}?page=0&size=20
     */
    @GetMapping("/client/{clientId}")
    public ResponseEntity<Page<BookingResponse>> getByClient(
            @PathVariable Long clientId,
            @RequestParam(defaultValue = "0")  int page,
            @RequestParam(defaultValue = "20") int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("scheduledAt").descending());
        return ResponseEntity.ok(bookingService.getBookingsByClient(clientId, pageable));
    }

    /**
     * GET /api/v1/bookings/consultant/{consultantId}?page=0&size=20
     */
    @GetMapping("/consultant/{consultantId}")
    public ResponseEntity<Page<BookingResponse>> getByConsultant(
            @PathVariable Long consultantId,
            @RequestParam(defaultValue = "0")  int page,
            @RequestParam(defaultValue = "20") int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("scheduledAt").ascending());
        return ResponseEntity.ok(bookingService.getBookingsByConsultant(consultantId, pageable));
    }

    /**
     * GET /api/v1/bookings/status/{status}?page=0&size=20
     * e.g. /api/v1/bookings/status/PENDING
     */
    @GetMapping("/status/{status}")
    public ResponseEntity<Page<BookingResponse>> getByStatus(
            @PathVariable BookingStatus status,
            @RequestParam(defaultValue = "0")  int page,
            @RequestParam(defaultValue = "20") int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("scheduledAt").ascending());
        return ResponseEntity.ok(bookingService.getBookingsByStatus(status, pageable));
    }

    // ── UPDATE STATUS ─────────────────────────────────────────────────────

    /**
     * PATCH /api/v1/bookings/{id}/status
     *
     * Request body (confirm example):
     * {
     *   "status": "CONFIRMED",
     *   "consultantId": 7,
     *   "internalNotes": "Confirmed via phone."
     * }
     *
     * Request body (reject example):
     * {
     *   "status": "REJECTED",
     *   "cancellationReason": "No available consultant."
     * }
     */
    @PatchMapping("/{id}/status")
    public ResponseEntity<BookingResponse> updateStatus(
            @PathVariable Long id,
            @Valid @RequestBody BookingStatusUpdateRequest request) {
        return ResponseEntity.ok(bookingService.updateBookingStatus(id, request));
    }

    // ── CANCEL ────────────────────────────────────────────────────────────

    /**
     * PATCH /api/v1/bookings/{id}/cancel
     *
     * Optional query param: reason=<string>
     */
    @PatchMapping("/{id}/cancel")
    public ResponseEntity<BookingResponse> cancelBooking(
            @PathVariable Long id,
            @RequestParam(required = false) String reason) {
        return ResponseEntity.ok(bookingService.cancelBooking(id, reason));
    }

    // ── DELETE ────────────────────────────────────────────────────────────

    /**
     * DELETE /api/v1/bookings/{id}
     * Requires ADMIN role — add @PreAuthorize("hasRole('ADMIN')") when Spring Security is configured.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBooking(@PathVariable Long id) {
        bookingService.deleteBooking(id);
        return ResponseEntity.noContent().build();
    }

    // ── HELPERS ───────────────────────────────────────────────────────────

    private Pageable buildPageable(int page, int size, String sortBy, String direction) {
        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();
        return PageRequest.of(page, size, sort);
    }
}

