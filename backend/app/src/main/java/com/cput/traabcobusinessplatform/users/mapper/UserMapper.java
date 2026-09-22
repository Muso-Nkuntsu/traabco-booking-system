package com.cput.traabcobusinessplatform.users.mapper;


import com.cput.traabcobusinessplatform.users.domain.UserEntity;
import com.cput.traabcobusinessplatform.users.dto.RegisterRequest;
import com.cput.traabcobusinessplatform.users.dto.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Muso Nkuntsu
 * MapStruct mapper for the user module
 * Handles conversation between the User entity and its DTOs
 * sp the service layer never manually maps fields
 * */

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "password", ignore = true)
    UserEntity toEntity(RegisterRequest request);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "firstName", source = "firstName")
    @Mapping(target = "lastName", source = "lastName")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "role", source = "role")
    @Mapping(target = "createdAt", source = "createdAt")

    UserResponse toResponse(UserEntity user);

}
