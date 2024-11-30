package com.isslpnu.backend.security.service;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.isslpnu.backend.api.dto.action.AuthenticationActionRequest;
import com.isslpnu.backend.constant.AuthenticationAction;
import com.isslpnu.backend.constant.Role;
import com.isslpnu.backend.exception.InvalidParameterException;
import com.isslpnu.backend.security.model.SessionDetails;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import static com.isslpnu.backend.security.constant.RequestHeaders.ALB_CLIENT_IP;
import static com.isslpnu.backend.security.constant.SecurityConstant.ROLE_PREFIX;
import static com.isslpnu.backend.security.constant.TokenClaims.CLAIM_ACTION;
import static com.isslpnu.backend.security.constant.TokenClaims.CLAIM_ROLE;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final JwtService jwtService;

    public String generateToken(UUID id, Role role) {
        return jwtService.generateToken(id.toString(), Map.of(CLAIM_ROLE, role.name()));
    }

    public String generateToken(String email, AuthenticationAction action) {
        return jwtService.generateToken(email, Map.of(CLAIM_ACTION, action.name()));
    }

    public void createSession(HttpServletRequest request) {
        SessionDetails sessionDetails = new SessionDetails();
        Optional.ofNullable(request.getHeader(AUTHORIZATION)).map(jwtService::verifyToken).ifPresent(decodedJwt -> {
            sessionDetails.setId(decodedJwt.getSubject());
            sessionDetails.setRole(decodedJwt.getClaim(CLAIM_ROLE).as(Role.class));
            sessionDetails.setAuthorities(List.of(new SimpleGrantedAuthority(String.join("_", ROLE_PREFIX, sessionDetails.getRole().name()))));
        });

        sessionDetails.setIp(StringUtils.firstNonBlank(request.getHeader(ALB_CLIENT_IP), request.getRemoteAddr()));
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(sessionDetails, null, sessionDetails.getAuthorities()));
    }

    public String decodeTokenFromRequest(AuthenticationActionRequest request, AuthenticationAction validAction) {
        DecodedJWT decodedJwt = jwtService.verifyPlainToken(request.getToken());
        AuthenticationAction requestedAction = decodedJwt.getClaim(CLAIM_ACTION).as(AuthenticationAction.class);

        if (Objects.isNull(requestedAction) || !validAction.equals(requestedAction)) {
            throw new InvalidParameterException("Invalid or missing action.");
        }

        return decodedJwt.getSubject();
    }
}
