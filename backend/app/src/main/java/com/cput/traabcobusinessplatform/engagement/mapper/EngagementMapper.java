package com.cput.traabcobusinessplatform.engagement.mapper;


import com.cput.traabcobusinessplatform.engagement.domain.Engagement;
import com.cput.traabcobusinessplatform.engagement.dto.EngagementResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**Muso Nkuntsu -231223722
 * MapStruct mapper for the Engagement module.
 * Maps nested entity relationships to flat ID fields
 * in the response DTO to avoid circular references.
 * client.clientid is used because Client entity uses
 * clientid as the field name instead of id.
 */


@Mapper(componentModel = "spring")
public interface EngagementMapper {
    @Mapping(target = "clientId", source = "client.clientid")
    @Mapping(target = "serviceId", source = "service.id")
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "bookingId", source = "booking.id")
    @Mapping(target = "startDate", source = "startDate")
    @Mapping(target = "endDate", source = "endDate")
    @Mapping(target = "status", source = "status")
    @Mapping(target = "createdAt", source = "createdAt")
    EngagementResponse toResponse(Engagement engagement);
}
