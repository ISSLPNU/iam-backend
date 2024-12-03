package com.isslpnu.backend.api;

import com.isslpnu.backend.api.dto.captcha.CaptchaRequest;
import com.isslpnu.backend.service.CaptchaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/captcha")
@RestController
public class CaptchaController {

    private final CaptchaService service;

    @PostMapping
    public void verifyCaptcha(@Valid @RequestBody CaptchaRequest request) {
        service.verifyCaptcha(request);
    }
}
