package com.isslpnu.backend.security.service;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.isslpnu.backend.api.dto.action.AuthenticationActionRequest;
import com.isslpnu.backend.constant.AuthenticationAction;
import com.isslpnu.backend.constant.Role;
import com.isslpnu.backend.exception.InvalidParameterException;
import com.isslpnu.backend.security.model.SessionDetails;
import com.isslpnu.backend.security.model.UserDetails;
import com.isslpnu.backend.security.util.SessionUtil;
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
        Optional.ofNullable(request.getHeader(AUTHORIZATION)).map(jwtService::verifyToken).ifPresent(decodedJwt -> {
            UserDetails userDetails = new UserDetails();
            userDetails.setId(decodedJwt.getSubject());
            userDetails.setRole(decodedJwt.getClaim(CLAIM_ROLE).as(Role.class));
            userDetails.setAuthorities(List.of(new SimpleGrantedAuthority(String.join("_", ROLE_PREFIX, userDetails.getRole().name()))));

            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities()));
        });

        SessionDetails sessionDetails = new SessionDetails();
        sessionDetails.setIp(StringUtils.firstNonBlank(request.getHeader(ALB_CLIENT_IP), request.getRemoteAddr()));
        SessionUtil.setSessionDetails(sessionDetails);
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
