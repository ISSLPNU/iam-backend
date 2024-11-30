package com.isslpnu.backend.api.dto;

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

}
