package com.isslpnu.backend.service;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.isslpnu.backend.api.dto.SignInResponse;
import com.isslpnu.backend.api.dto.oauth.OAuthSignInRequest;
import com.isslpnu.backend.constant.Role;
import com.isslpnu.backend.domain.User;
import com.isslpnu.backend.exception.InvalidParameterException;
import com.isslpnu.backend.mapper.AuthMapper;
import com.isslpnu.backend.security.service.TokenService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OAuthService {

    private static final String FIRST_NAME = "firstName";
    private static final String LAST_NAME = "lastName";
    private static final String EMAIL = "email";

    private final AuthMapper authMapper;
    private final UserService userService;
    private final TokenService tokenService;
    private final GoogleIdTokenVerifier verifier;

    public SignInResponse signIn(OAuthSignInRequest request) {
        Map<String, String> data = verify(request.getToken());
        if (Objects.isNull(data)) {
            throw new InvalidParameterException("Invalid oauth token.");
        }

        if (userService.existsByEmail(data.get(EMAIL))) {
            User user = userService.getByEmail(data.get(EMAIL));
            return authMapper.asSignInResponse(tokenService.generateToken(user.getId(), user.getRole()), user.getOauthProvider(), false);
        }

        User user = new User();
        user.setFirstName(data.get(FIRST_NAME));
        user.setLastName(data.get(LAST_NAME));
        user.setEmail(data.get(EMAIL));
        user.setTfaEnabled(false);
        user.setEmailConfirmed(true);
        user.setRole(Role.USER);
        user.setOauthProvider(request.getProvider());
        User created = userService.create(user);

        return authMapper.asSignInResponse(tokenService.generateToken(created.getId(), created.getRole()), user.getOauthProvider(), false);
    }

    private Map<String, String> verify(String token) {
        return Optional.ofNullable(token)
                .map(this::getVerifyIdToken)
                .map(GoogleIdToken::getPayload)
                .map(payload -> {
                    Map<String, String> map = new HashMap<>();
                    map.put(FIRST_NAME, (String) payload.get("given_name"));
                    map.put(LAST_NAME, (String) payload.get("family_name"));
                    map.put(EMAIL, payload.getEmail());
                    return map;
                })
                .orElse(null);
    }

    @SneakyThrows
    private GoogleIdToken getVerifyIdToken(String token) {
        return verifier.verify(token);
    }

}
