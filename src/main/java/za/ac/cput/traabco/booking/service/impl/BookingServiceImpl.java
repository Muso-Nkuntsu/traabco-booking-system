package za.ac.cput.traabco.booking.service.impl;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import za.ac.cput.traabco.booking.domain.enums.BookingStatus;
import za.ac.cput.traabco.booking.domain.model.Booking;
import za.ac.cput.traabco.booking.dto.BookingRequest;
import za.ac.cput.traabco.booking.exception.BookingExceptions;
import za.ac.cput.traabco.booking.mapper.BookingMapper;
import za.ac.cput.traabco.booking.repository.BookingRepository;
import za.ac.cput.traabco.booking.dto.BookingResponse;
import za.ac.cput.traabco.booking.dto.BookingStatusUpdateRequest;
import za.ac.cput.traabco.booking.service.BookingService;

import java.time.LocalDateTime;
import java.util.EnumSet;
import java.util.Set;

/**
 * Core booking business logic.
 *
 * State machine (allowed transitions):
 *  PENDING    → CONFIRMED | REJECTED | CANCELLED
 *  CONFIRMED  → IN_PROGRESS | CANCELLED
 *  IN_PROGRESS→ COMPLETED | CANCELLED
 *  COMPLETED  → (terminal)
 *  CANCELLED  → (terminal)
 *  REJECTED   → (terminal)
 */
@org.springframework.stereotype.Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final BookingMapper bookingMapper;

    // ── External repository stubs (inject your real ones) ─────────────────
    // In a real project these would be injected from their respective modules.
    private final com.businessplatform.booking.repository.ClientRepository  clientRepository;
    private final com.businessplatform.booking.repository.ServiceRepository serviceRepository;
    private final com.businessplatform.booking.repository.UserRepository    userRepository;

    // ── Allowed terminal statuses (no further transitions) ────────────────
    private static final Set<BookingStatus> TERMINAL_STATUSES =
            EnumSet.of(BookingStatus.COMPLETED, BookingStatus.CANCELLED, BookingStatus.REJECTED);

    // ── Cancellable statuses (by client) ──────────────────────────────────
    private static final Set<BookingStatus> CLIENT_CANCELLABLE =
            EnumSet.of(BookingStatus.PENDING, BookingStatus.CONFIRMED);

    // ═════════════════════════════════════════════════════════════════════
    //  CREATE
    // ═════════════════════════════════════════════════════════════════════

    @Override
    @Transactional
    public BookingResponse createBooking(BookingRequest request) {
        log.info("Creating booking for clientId={} serviceId={}", request.getClientId(), request.getServiceId());

        Client  client  = findClientOrThrow(request.getClientId());
        Service service = findServiceOrThrow(request.getServiceId());

        // Resolve duration: use request value or fall back to service default
        int duration = request.getDurationMinutes() != null
                ? request.getDurationMinutes()
                : (service.getDefaultDurationMinutes() != null ? service.getDefaultDurationMinutes() : 60);

        LocalDateTime end = request.getScheduledAt().plusMinutes(duration);

        // Resolve consultant and check for scheduling conflicts
        User consultant = null;
        if (request.getConsultantId() != null) {
            consultant = findUserOrThrow(request.getConsultantId());
            checkForConflict(consultant.getId(), request.getScheduledAt(), end, null);
        }

        Booking booking = Booking.builder()
                .client(client)
                .service(service)
                .consultant(consultant)
                .status(BookingStatus.PENDING)
                .scheduledAt(request.getScheduledAt())
                .durationMinutes(duration)
                .agreedPrice(request.getAgreedPrice() != null
                        ? request.getAgreedPrice()
                        : service.getBasePrice())
                .clientNotes(request.getClientNotes())
                .build();

        Booking saved = bookingRepository.save(booking);
        log.info("Booking created with id={}", saved.getId());
        return bookingMapper.toResponse(saved);
    }

    // ═════════════════════════════════════════════════════════════════════
    //  READ
    // ═════════════════════════════════════════════════════════════════════

    @Override
    public BookingResponse getBookingById(Long id) {
        return bookingMapper.toResponse(findBookingOrThrow(id));
    }

    @Override
    public Page<BookingResponse> getAllBookings(Pageable pageable) {
        return bookingRepository.findAll(pageable).map(bookingMapper::toResponse);
    }

    @Override
    public Page<BookingResponse> getBookingsByClient(Long clientId, Pageable pageable) {
        return bookingRepository.findByClientId(clientId, pageable).map(bookingMapper::toResponse);
    }

    @Override
    public Page<BookingResponse> getBookingsByConsultant(Long consultantId, Pageable pageable) {
        return bookingRepository.findByConsultantId(consultantId, pageable).map(bookingMapper::toResponse);
    }

    @Override
    public Page<BookingResponse> getBookingsByStatus(BookingStatus status, Pageable pageable) {
        return bookingRepository.findByStatus(status, pageable).map(bookingMapper::toResponse);
    }

    // ═════════════════════════════════════════════════════════════════════
    //  UPDATE STATUS
    // ═════════════════════════════════════════════════════════════════════

    @Override
    @Transactional
    public BookingResponse updateBookingStatus(Long id, BookingStatusUpdateRequest request) {
        Booking booking = findBookingOrThrow(id);

        validateTransition(booking.getStatus(), request.getStatus());

        // (Re-)assign consultant when confirming, if provided
        if (request.getConsultantId() != null) {
            User consultant = findUserOrThrow(request.getConsultantId());

            // Check for conflicts (exclude this booking from the check)
            LocalDateTime end = booking.getScheduledAt()
                    .plusMinutes(booking.getDurationMinutes() != null ? booking.getDurationMinutes() : 60);
            checkForConflict(consultant.getId(), booking.getScheduledAt(), end, booking.getId());

            booking.setConsultant(consultant);
        }

        booking.setStatus(request.getStatus());

        if (request.getInternalNotes() != null) {
            booking.setInternalNotes(request.getInternalNotes());
        }
        if (request.getCancellationReason() != null) {
            booking.setCancellationReason(request.getCancellationReason());
        }

        log.info("Booking id={} transitioned to status={}", id, request.getStatus());
        return bookingMapper.toResponse(bookingRepository.save(booking));
    }

    // ═════════════════════════════════════════════════════════════════════
    //  CANCEL (client-facing)
    // ═════════════════════════════════════════════════════════════════════

    @Override
    @Transactional
    public BookingResponse cancelBooking(Long id, String cancellationReason) {
        Booking booking = findBookingOrThrow(id);

        if (!CLIENT_CANCELLABLE.contains(booking.getStatus())) {
            throw new BookingExceptions.InvalidBookingStateException(
                    "Booking in status " + booking.getStatus() + " cannot be cancelled by the client.");
        }

        booking.setStatus(BookingStatus.CANCELLED);
        booking.setCancellationReason(cancellationReason);

        log.info("Booking id={} cancelled by client.", id);
        return bookingMapper.toResponse(bookingRepository.save(booking));
    }

    // ═════════════════════════════════════════════════════════════════════
    //  DELETE
    // ═════════════════════════════════════════════════════════════════════

    @Override
    @Transactional
    public void deleteBooking(Long id) {
        Booking booking = findBookingOrThrow(id);
        bookingRepository.delete(booking);
        log.info("Booking id={} hard-deleted.", id);
    }

    // ═════════════════════════════════════════════════════════════════════
    //  PRIVATE HELPERS
    // ═════════════════════════════════════════════════════════════════════

    private Booking findBookingOrThrow(Long id) {
        return bookingRepository.findById(id)
                .orElseThrow(() -> new BookingExceptions.ResourceNotFoundException("Booking not found with id: " + id));
    }

    private Client findClientOrThrow(Long id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new BookingExceptions.ResourceNotFoundException("Client not found with id: " + id));
    }

    private Service findServiceOrThrow(Long id) {
        return serviceRepository.findById(id)
                .orElseThrow(() -> new BookingExceptions.ResourceNotFoundException("Service not found with id: " + id));
    }

    private User findUserOrThrow(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new BookingExceptions.ResourceNotFoundException("User (Consultant) not found with id: " + id));
    }

    /**
     * Validates that the transition from {@code current} to {@code next} is permitted.
     */
    private void validateTransition(BookingStatus current, BookingStatus next) {
        if (TERMINAL_STATUSES.contains(current)) {
            throw new BookingExceptions.InvalidBookingStateException(
                    "Cannot transition from terminal status: " + current);
        }

        boolean allowed = switch (current) {
            case PENDING     -> EnumSet.of(BookingStatus.CONFIRMED, BookingStatus.REJECTED, BookingStatus.CANCELLED).contains(next);
            case CONFIRMED   -> EnumSet.of(BookingStatus.IN_PROGRESS, BookingStatus.CANCELLED).contains(next);
            case IN_PROGRESS -> EnumSet.of(BookingStatus.COMPLETED, BookingStatus.CANCELLED).contains(next);
            default          -> false;
        };

        if (!allowed) {
            throw new BookingExceptions.InvalidBookingStateException(
                    "Transition from " + current + " to " + next + " is not permitted.");
        }
    }

    /**
     * Checks whether the given consultant has an overlapping active booking.
     *
     * @param excludeId booking ID to exclude from the check (for updates); null for new bookings
     */
    private void checkForConflict(Long consultantId, LocalDateTime start, LocalDateTime end, Long excludeId) {
        boolean conflict = bookingRepository.existsOverlappingBooking(consultantId, start, end, excludeId);
        if (conflict) {
            throw new BookingExceptions.SchedulingConflictException(
                    "Consultant id=" + consultantId + " already has a booking overlapping the requested time slot.");
        }
    }
}

