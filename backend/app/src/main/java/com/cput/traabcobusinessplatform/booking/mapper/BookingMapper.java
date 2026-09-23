package com.cput.traabcobusinessplatform.booking.mapper;


import com.cput.traabcobusinessplatform.booking.domain.Booking;
import com.cput.traabcobusinessplatform.booking.dto.BookingResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**Muso Nkuntsu -231223722
 * */

@Mapper(componentModel = "spring")
public interface BookingMapper {
    @Mapping(target = "clientId", source = "client.id")
    @Mapping(target = "serviceId", source = "service.id")
    @Mapping(target = "userId", source = "user.id")
    BookingResponse toResponse(Booking booking);
}
