package za.ac.cput.traabco.booking.domain.model;

import za.ac.cput.traabco.booking.domain.enums.BookingStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Entity
@Table(name = "bookings", indexes = {
        @Index(name = "idx_booking_client_id", columnList = "client_id"),
        @Index(name = "idx_booking_status",    columnList = "status"),
        @Index(name = "idx_booking_scheduled",  columnList = "scheduled_at")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //The consultant assigned to this booking (may be null until confirmed)
    @ManyToOne(fetch - FetchType.EAGER, optional = false)
    @JoinColumn(name = "service_id", nullable = false)
    private Service service;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "consultant_id")
    private User consultant;

    // ──────────────────────────────────────────────
    // Core fields
    // ──────────────────────────────────────────────

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @Builder.Default
    private BookingStatus status = BookingStatus.PENDING;

    /** When the session / appointment is scheduled to start. */
    @Column(name = "scheduled_at", nullable = false)
    private LocalDateTime scheduledAt;

    /** Expected duration in minutes. */
    @Column(name = "duration_minutes")
    private Integer durationMinutes;

    /** Agreed price at the time of booking (snapshot — service price may change later). */
    @Column(name = "agreed_price", precision = 10, scale = 2)
    private BigDecimal agreedPrice;

    /** Free-text notes left by the client at booking time. */
    @Column(name = "client_notes", columnDefinition = "TEXT")
    private String clientNotes;

    /** Internal notes added by the consultant or admin. */
    @Column(name = "internal_notes", columnDefinition = "TEXT")
    private String internalNotes;

    /** Reason provided when a booking is canceled or rejected. */
    @Column(name = "cancelation_reason", length = 500)
    private String cancellationReason;

    // ──────────────────────────────────────────────
    // Audit fields
    // ──────────────────────────────────────────────

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;


}
