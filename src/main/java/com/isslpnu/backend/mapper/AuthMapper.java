package com.isslpnu.backend.mapper;

import com.isslpnu.backend.api.dto.SignInResponse;
import com.isslpnu.backend.api.dto.SingUpRequest;
import com.isslpnu.backend.constant.OAuthProvider;
import com.isslpnu.backend.domain.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AuthMapper {

    @Mapping(target = "password", ignore = true)
    @Mapping(target = "emailConfirmed", constant = "false")
    @Mapping(target = "tfaEnabled", constant = "false")
    @Mapping(target = "role", constant = "USER")
    @Mapping(target = "oauthProvider", constant = "INTERNAL")
    User asUser(SingUpRequest request);

    @Mapping(target = "emailConfirmed", constant = "true")
    SignInResponse asSignInResponse(String token, OAuthProvider oAuthProvider, boolean twoFactor);

    default SignInResponse asSignInResponse(boolean twoFactor, boolean emailConfirmed) {
        return SignInResponse.builder()
                .twoFactor(emailConfirmed)
                .twoFactor(twoFactor)
                .oAuthProvider(OAuthProvider.INTERNAL)
                .build();
    }

    default SignInResponse asSignInResponse(OAuthProvider oAuthProvider){
        return SignInResponse.builder()
                .oAuthProvider(oAuthProvider)
                .build();
    }

}
