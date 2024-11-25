package com.isslpnu.backend.api.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthenticationActionRequest {

    @NotBlank
    private String token;

}
