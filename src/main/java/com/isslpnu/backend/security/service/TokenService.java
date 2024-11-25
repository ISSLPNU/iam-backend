package com.isslpnu.backend.security.service;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.isslpnu.backend.constant.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.isslpnu.backend.security.constant.SecurityConstant.ROLE_PREFIX;
import static com.isslpnu.backend.security.constant.TokenClaims.CLAIM_ROLE;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final JwtService jwtService;

    public void authenticate(String token) {
        Optional.ofNullable(token).map(jwtService::verifyToken).ifPresent(this::authenticate);
    }

    private void authenticate(DecodedJWT decodedJwt) {
        String userId = decodedJwt.getSubject();
        Role role = decodedJwt.getClaim(CLAIM_ROLE).as(Role.class);

        List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(String.join("_", ROLE_PREFIX, role.name())));
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(userId, null, authorities));
    }
}
