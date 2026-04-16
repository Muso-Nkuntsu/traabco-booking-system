package za.ac.cput.traabco.booking.mapper;


import org.springframework.stereotype.Component;
import za.ac.cput.traabco.booking.domain.model.Booking;
import za.ac.cput.traabco.booking.dto.BookingResponse;

@Component
public class BookingMapper {
    public BookingResponse toResponse(Booking booking){
        if(booking ==null) return null;

        return BookingResponse.builder()
                .id(booking.getId())
                .status(booking.getStatus())
                // Client
                .clientId(booking.getClient().getId())
                .clientName(booking.getClient().getFullName())
                // Service
                .serviceId(booking.getService().getId())
                .serviceName(booking.getService().getName())
                // Consultant (nullable)
                .consultantId(booking.getConsultant() != null ? booking.getConsultant().getId() : null)
                .consultantName(booking.getConsultant() != null ? booking.getConsultant().getFullName() : null)
                // Scheduling
                .scheduledAt(booking.getScheduledAt())
                .durationMinutes(booking.getDurationMinutes())
                .agreedPrice(booking.getAgreedPrice())
                // Notes
                .clientNotes(booking.getClientNotes())
                .internalNotes(booking.getInternalNotes())
                .cancellationReason(booking.getCancellationReason())
                // Audit
                .createdAt(booking.getCreatedAt())
                .updatedAt(booking.getUpdatedAt())
                .build();

    }
}
