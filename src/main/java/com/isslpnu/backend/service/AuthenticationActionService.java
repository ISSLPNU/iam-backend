package com.isslpnu.backend.service;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.isslpnu.backend.api.dto.AuthenticationActionRequest;
import com.isslpnu.backend.domain.ConfirmationToken;
import com.isslpnu.backend.security.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.isslpnu.backend.security.constant.TokenClaims.CLAIM_ACTION;

@Service
@RequiredArgsConstructor
public class AuthenticationActionService {

    private final JwtService jwtService;
    private final ConfirmationTokenService confirmationTokenService;

    public void doAction(AuthenticationActionRequest request) {
        ConfirmationToken confirmationToken = confirmationTokenService.findByToken(request.getToken());
        DecodedJWT decodedJwt = jwtService.verifyPlainToken(confirmationToken.getToken());

        if (!decodedJwt.getClaims().containsKey(CLAIM_ACTION)) {

        }
    }
}
