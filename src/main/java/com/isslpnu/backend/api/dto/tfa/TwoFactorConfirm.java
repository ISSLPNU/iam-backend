package com.isslpnu.backend.api.dto.tfa;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TwoFactorConfirm {

    @NotNull
    private Long confirmCode;
    @NotBlank
    private String token;

}
