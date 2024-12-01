package com.isslpnu.backend.api.dto.action;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TwoFactorRequest extends AuthenticationActionRequest {

    @NotNull
    private Long secret;

}
