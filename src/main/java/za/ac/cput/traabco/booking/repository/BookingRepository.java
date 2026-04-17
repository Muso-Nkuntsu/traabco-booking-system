package za.ac.cput.traabco.booking.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import za.ac.cput.traabco.booking.domain.enums.BookingStatus;
import za.ac.cput.traabco.booking.domain.model.Booking;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    // ── Paginated queries ──────────────────────────────────────────────────

    Page<Booking> findByClientId(Long clientId, Pageable pageable);

    Page<Booking> findByConsultantId(Long consultantId, Pageable pageable);

    Page<Booking> findByStatus(BookingStatus status, Pageable pageable);

    // ── Existence / conflict checks ────────────────────────────────────────

    /**
     * Checks whether a consultant already has a booking that overlaps
     * the requested [start, end) window (exclusive of a given booking id
     * so we can use the same query for update scenarios).
     */
    @Query("""
            SELECT COUNT(b) > 0
            FROM Booking b
            WHERE b.consultant.id = :consultantId
              AND b.status NOT IN ('CANCELLED', 'REJECTED')
              AND b.scheduledAt < :end
              AND FUNCTION('ADDTIME', b.scheduledAt,
                           FUNCTION('SEC_TO_TIME', b.durationMinutes * 60)) > :start
              AND (:excludeId IS NULL OR b.id <> :excludeId)
            """)
    boolean existsOverlappingBooking(
            @Param("consultantId") Long consultantId,
            @Param("start") LocalDateTime start,
            @Param("end")   LocalDateTime end,
            @Param("excludeId") Long excludeId
    );

    // ── Reporting / dashboard ──────────────────────────────────────────────

    @Query("""
            SELECT b FROM Booking b
            WHERE b.scheduledAt BETWEEN :from AND :to
            ORDER BY b.scheduledAt ASC
            """)
    List<Booking> findAllInDateRange(
            @Param("from") LocalDateTime from,
            @Param("to")   LocalDateTime to
    );

    long countByStatus(BookingStatus status);
}

