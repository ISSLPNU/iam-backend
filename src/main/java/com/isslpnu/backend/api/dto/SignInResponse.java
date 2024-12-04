package com.isslpnu.backend.api.dto;

import com.isslpnu.backend.constant.OAuthProvider;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class SignInResponse {

    private String token;
    private boolean twoFactor;
    private boolean emailConfirmed;
    private OAuthProvider oauthProvider;

}
