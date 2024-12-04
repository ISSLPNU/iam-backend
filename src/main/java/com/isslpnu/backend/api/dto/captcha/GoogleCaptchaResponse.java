package com.isslpnu.backend.api.dto.captcha;

import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

@Getter
@Setter
public class GoogleCaptchaResponse {

    private boolean success;
    private ZonedDateTime challengeTs;
    private String hostname;

}
