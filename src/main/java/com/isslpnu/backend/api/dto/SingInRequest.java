package com.isslpnu.backend.api.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SingInRequest {

    @NotBlank
    private String email;
    @NotBlank
    private String password;

}
