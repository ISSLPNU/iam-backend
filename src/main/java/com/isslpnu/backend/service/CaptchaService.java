package com.isslpnu.backend.service;

import com.isslpnu.backend.api.dto.captcha.CaptchaRequest;
import com.isslpnu.backend.api.dto.captcha.GoogleCaptchaResponse;
import com.isslpnu.backend.exception.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Objects;

@RequiredArgsConstructor
@Service
public class CaptchaService {

    private static final String SECRET_PARAM = "secret";
    private static final String RESPONSE_PARAM = "response";

    private final WebClient webClient;

    @Value("${google.recaptcha.key.secret}")
    private String googleRecaptchaSecretKey;
    @Value("${google.recaptcha.verifyUrl}")
    private String googleRecaptchaVerifyUrl;

    public void verifyCaptcha(CaptchaRequest request) {
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add(SECRET_PARAM, googleRecaptchaSecretKey);
        body.add(RESPONSE_PARAM, request.getToken());
        GoogleCaptchaResponse response = webClient.post()
                .uri(googleRecaptchaVerifyUrl)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .bodyValue(body)
                .retrieve()
                .bodyToMono(GoogleCaptchaResponse.class)
                .onErrorResume(Exception.class, e -> {
                    GoogleCaptchaResponse errorResponse = new GoogleCaptchaResponse();
                    errorResponse.setSuccess(false);
                    return Mono.just(errorResponse);
                })
                .block();

        if (Objects.nonNull(response) && !response.isSuccess()) {
            throw new ValidationException("validation.authentication.captcha.invalid", "Invalid Captcha value. Try again.");
        }
    }
}
