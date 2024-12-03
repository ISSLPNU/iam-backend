package com.isslpnu.backend.service;

import com.isslpnu.backend.api.dto.captcha.CaptchaRequest;
import com.isslpnu.backend.exception.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

@RequiredArgsConstructor
@Service
public class CaptchaService {

    private static final String SECRET_PARAM = "secret";
    private static final String RESPONSE_PARAM = "response";

    private final WebClient webClient;

    @Value("${google.recaptcha.secret}")
    private String googleRecaptchaSecret;
    @Value("${google.recaptcha.verifyUrl}")
    private String googleRecaptchaVerifyUrl;

    public void verifyCaptcha(CaptchaRequest request) {
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add(SECRET_PARAM, googleRecaptchaSecret);
        body.add(RESPONSE_PARAM, request.getToken());
        webClient.post()
                .uri(googleRecaptchaVerifyUrl)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .bodyValue(body)
                .retrieve()
                .toBodilessEntity()
                .onErrorMap(Exception.class, e -> new ValidationException("validation.authentication.captcha.invalid", "Invalid Captcha value. Try again."));
    }
}
