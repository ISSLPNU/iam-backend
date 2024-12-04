package com.isslpnu.backend.api;

import com.isslpnu.backend.api.dto.SignInResponse;
import com.isslpnu.backend.api.dto.oauth.OAuthSignInRequest;
import com.isslpnu.backend.service.OAuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/v2/oauth")
@RestController
public class OAuthController {

    private final OAuthService oAuthService;

    @PostMapping("/login")
    public SignInResponse oAuthLogin(@Valid @RequestBody OAuthSignInRequest request) {
        return oAuthService.signIn(request);
    }
}
