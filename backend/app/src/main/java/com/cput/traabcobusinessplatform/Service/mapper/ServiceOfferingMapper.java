package com.cput.traabcobusinessplatform.Service.mapper;

import com.cput.traabcobusinessplatform.Service.dto.ServiceRequest;
import com.cput.traabcobusinessplatform.Service.domain.ServiceEntity;
import com.cput.traabcobusinessplatform.Service.dto.ServiceResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Muso Nkuntsu-231223722
 * */

@Mapper(componentModel = "spring")
public interface ServiceOfferingMapper {


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    ServiceEntity toEntity(ServiceRequest request);

    @Mapping(target = "id", source ="id")
    @Mapping(target = "serviceName", source = "serviceName")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "price", source = "price")
    @Mapping(target = "defaultDurationMinutes", source = "defaultDurationMinutes")
    @Mapping(target = "category", source = "category")
    @Mapping(target = "isActive", source = "isActive")
    @Mapping(target = "createdAt", source = "createdAt")
    ServiceResponse toResponse(ServiceEntity service);

}
