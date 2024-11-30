package com.isslpnu.backend.api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignInResponse {

    private String token;
    private boolean twoFactor;
    private boolean emailConfirmed;

}
