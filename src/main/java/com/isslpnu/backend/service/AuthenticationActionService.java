package com.isslpnu.backend.service;

import com.isslpnu.backend.api.dto.SignInResponse;
import com.isslpnu.backend.api.dto.action.AuthenticationActionRequest;
import com.isslpnu.backend.api.dto.action.PasswordRestoreActionRequest;
import com.isslpnu.backend.api.dto.action.TwoFactorRequest;
import com.isslpnu.backend.constant.AuthenticationAction;
import com.isslpnu.backend.domain.ConfirmationToken;
import com.isslpnu.backend.domain.TwoFactor;
import com.isslpnu.backend.domain.User;
import com.isslpnu.backend.exception.ValidationException;
import com.isslpnu.backend.mapper.AuthMapper;
import com.isslpnu.backend.repository.TwoFactorRepository;
import com.isslpnu.backend.security.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthenticationActionService {

    private final AuthMapper authMapper;
    private final UserService userService;
    private final TokenService tokenService;
    private final PasswordEncoder passwordEncoder;
    private final TwoFactorService twoFactorService;
    private final ConfirmationTokenService confirmationTokenService;

    @Value("${default_ttl.confirmation_action}")
    private int actionTtl;
    private final TwoFactorRepository twoFactorRepository;

    @Transactional
    public void confirmSignUp(AuthenticationActionRequest request) {
        String email = tokenService.decodeTokenFromRequest(request, AuthenticationAction.SIGN_UP_CONFIRMATION);
        ConfirmationToken confirmationToken = confirmationTokenService.getByToken(request.getToken(), LocalDateTime.now().minusMinutes(actionTtl));
        User user = userService.getByEmail(email);
        user.setEmailConfirmed(true);
        userService.update(user);
        confirmationTokenService.delete(confirmationToken);
    }

    @Transactional
    public SignInResponse twoFactorConfirm(TwoFactorRequest request) {
        String email = tokenService.decodeTokenFromRequest(request, AuthenticationAction.TWO_FACTOR);
        ConfirmationToken confirmationToken = confirmationTokenService.getByToken(request.getToken(), LocalDateTime.now().minusMinutes(actionTtl));
        TwoFactor twoFactor = twoFactorService.getByConfirmationTokenId(confirmationToken.getId());

        if (ObjectUtils.notEqual(request.getSecret(), twoFactor.getSecret())) {
            throw new ValidationException("validation.authentication.twoFactor.notEqualSecrets", "Code is incorrect");
        }

        twoFactorService.delete(twoFactor);
        confirmationTokenService.delete(confirmationToken);
        twoFactorRepository.delete(twoFactor);

        User user = userService.getByEmail(email);
        return authMapper.asSignInResponse(tokenService.generateToken(user.getId(), user.getRole()), false);
    }

    @Transactional
    public void restorePasswordConfirm(PasswordRestoreActionRequest request) {
        String email = tokenService.decodeTokenFromRequest(request, AuthenticationAction.PASSWORD_RESTORE);
        ConfirmationToken confirmationToken = confirmationTokenService.getByToken(request.getToken(), LocalDateTime.now().minusMinutes(actionTtl));
        User user = userService.getByEmail(email);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userService.update(user);
        confirmationTokenService.delete(confirmationToken);
    }
}
