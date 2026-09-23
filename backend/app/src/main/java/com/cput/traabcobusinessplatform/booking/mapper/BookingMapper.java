package com.cput.traabcobusinessplatform.booking.mapper;


import com.cput.traabcobusinessplatform.booking.domain.Booking;
import com.cput.traabcobusinessplatform.booking.dto.BookingResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**Muso Nkuntsu -231223722
 * */

@Mapper(componentModel = "spring")
public interface BookingMapper {
    @Mapping(target = "clientId", source = "client.clientid")
    @Mapping(target = "serviceId", source = "service.id")
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "bookingDate", source = "bookingDate")
    @Mapping(target = "scheduleAt", source = "scheduleAt")
    @Mapping(target = "status", source = "status")
    @Mapping(target = "notes", source = "notes")
    BookingResponse toResponse(Booking booking);
}
