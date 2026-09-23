package com.cput.traabcobusinessplatform.client.mapper;

import com.cput.traabcobusinessplatform.client.dto.ClientRequest;
import com.cput.traabcobusinessplatform.client.dto.ClientResponse;
import com.cput.traabcobusinessplatform.client.domain.Client;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ClientMapper {

    @Mapping(target = "clientid", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    Client toEntity(ClientRequest request);

    @Mapping(target = "id", source = "clientid")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "taxNumber", source = "taxNumber")
    @Mapping(target = "firstName", source = "firstName")
    @Mapping(target = "lastName", source = "lastName")
    @Mapping(target = "companyName", source = "companyName")
    @Mapping(target = "address", source = "address")
    @Mapping(target = "createdAt", source = "createdAt")
    ClientResponse toResponse(Client client);


    @Mapping(target = "clientid", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    void updateEntityFromRequest(ClientRequest request, @MappingTarget Client client);
}
