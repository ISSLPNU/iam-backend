package com.isslpnu.backend.api.dto.action;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordRestoreActionRequest extends AuthenticationActionRequest {

    @NotBlank
    private String password;

}
