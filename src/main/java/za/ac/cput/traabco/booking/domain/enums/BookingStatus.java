package za.ac.cput.traabco.booking.domain.enums;

public enum BookingStatus {
    /** Booking has been submitted but not yet reviewed */
    PENDING,

    /** Booking has been confirmed by a Consultant or Admin */
    CONFIRMED,

    /** Booking is currently in progress */
    IN_PROGRESS,

    /** Booking has been successfully completed */
    COMPLETED,

    /** Booking was cancelled by the client or admin */
    CANCELLED,

    /** Booking was rejected (e.g., no availability) */
    REJECTED
}
