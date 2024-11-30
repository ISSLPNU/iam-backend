package com.isslpnu.backend.service;

import com.isslpnu.backend.api.dto.action.AuthenticationActionRequest;
import com.isslpnu.backend.api.dto.action.PasswordRestoreActionRequest;
import com.isslpnu.backend.constant.AuthenticationAction;
import com.isslpnu.backend.domain.ConfirmationToken;
import com.isslpnu.backend.domain.User;
import com.isslpnu.backend.exception.NotFoundException;
import com.isslpnu.backend.security.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@RequiredArgsConstructor
public class AuthenticationActionService {

    private final UserService userService;
    private final TokenService tokenService;
    private final PasswordEncoder passwordEncoder;
    private final ConfirmationTokenService confirmationTokenService;

    public void confirmSignUp(AuthenticationActionRequest request) {
        String email = tokenService.decodeTokenFromRequest(request, AuthenticationAction.SIGN_UP_CONFIRMATION);
        ConfirmationToken confirmationToken = confirmationTokenService.findByToken(request.getToken());
        User user = userService.getByEmail(email);
        user.setEmailConfirmed(true);
        userService.update(user);
        confirmationTokenService.delete(confirmationToken);
    }

    public void twoFactorConfirm(AuthenticationActionRequest request) {
        String email = tokenService.decodeTokenFromRequest(request, AuthenticationAction.TWO_FACTOR);
        ConfirmationToken confirmationToken = confirmationTokenService.findByToken(request.getToken());
        //TODO 11/26/24: ////TODO 11/26/24://TODO 11/26/24://TODO 11/26/24:
    }

    public void restorePasswordConfirm(PasswordRestoreActionRequest request) {
        String email = tokenService.decodeTokenFromRequest(request, AuthenticationAction.PASSWORD_RESTORE);
        ConfirmationToken confirmationToken = confirmationTokenService.findByToken(request.getToken());
        User user = userService.getByEmail(email);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userService.update(user);
        confirmationTokenService.delete(confirmationToken);
    }
}
