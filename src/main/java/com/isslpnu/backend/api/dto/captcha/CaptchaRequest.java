package com.isslpnu.backend.api.dto.captcha;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CaptchaRequest {

    @NotBlank
    private String captchaToken;

}
