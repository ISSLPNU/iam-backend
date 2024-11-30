package com.isslpnu.backend.mapper;

import com.isslpnu.backend.api.dto.SignInResponse;
import com.isslpnu.backend.api.dto.SingUpRequest;
import com.isslpnu.backend.domain.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AuthMapper {

    @Mapping(target = "password", ignore = true)
    @Mapping(target = "emailConfirmed", constant = "false")
    @Mapping(target = "tfaEnabled", constant = "false")
    @Mapping(target = "role", constant = "USER")
    User asUser(SingUpRequest request);

    SignInResponse asSignInResponse(String token, boolean twoFactor, boolean emailConfirmed);

}
