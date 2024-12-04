package com.isslpnu.backend.api.dto;

import com.isslpnu.backend.api.dto.captcha.CaptchaRequest;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SingInRequest extends CaptchaRequest {

    @NotBlank
    private String email;
    @NotBlank
    private String password;

}
